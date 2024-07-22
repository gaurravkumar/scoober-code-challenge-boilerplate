package com.justeattakeaway.codechallenge.service;

import com.justeattakeaway.codechallenge.configuration.RabbitMQConfig;
import com.justeattakeaway.codechallenge.model.player.Player;
import com.justeattakeaway.codechallenge.model.player.dto.PlayerCreationRequest;
import com.justeattakeaway.codechallenge.model.player.dto.PlayerCreationResponse;
import com.justeattakeaway.codechallenge.repository.PlayerPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PlayerServiceImpl implements PlayerService {
    private PlayerPersistence playerPersistence;

    private RabbitMQConfig rabbitMQConfig;

    @Autowired
    public PlayerServiceImpl(PlayerPersistence playerPersistence, RabbitMQConfig rabbitMQConfig) {
        this.playerPersistence = playerPersistence;
        this.rabbitMQConfig = rabbitMQConfig;
    }

    public PlayerCreationResponse createPlayer(PlayerCreationRequest playerCreationRequest) {
        String queueName = String.join("-", playerCreationRequest.getName(), UUID.randomUUID().toString());
        Player player = Player.builder()
                .name(playerCreationRequest.getName())
                .queueName(queueName)
                .uniqueLoginName(playerCreationRequest.getUniqueLoginName())
                .build();
        var uniqueNameFromDb = playerPersistence.getPlayerByLoginName(player.getUniqueLoginName());
        if (uniqueNameFromDb != null) {
            PlayerCreationResponse playerCreationResponse = PlayerCreationResponse.builder()
                    .errrorMessage("Player with this unique login name already exists").build();
            return playerCreationResponse;
        }
        rabbitMQConfig.createQueue(player.getQueueName());
        var persistedPlayer = playerPersistence.savePlayer(player);
        PlayerCreationResponse playerCreationResponse = PlayerCreationResponse.builder()
                .id(persistedPlayer.getId())
                .name(persistedPlayer.getName())
                .queueName(persistedPlayer.getQueueName())
                .uniqueLoginName(persistedPlayer.getUniqueLoginName())
                .build();
        return playerCreationResponse;
    }

    public Player getPlayerByUniqueLoginName(String uniqueLoginName) {
        if (uniqueLoginName == null || uniqueLoginName.isEmpty()) {
            return null;
        }
        return playerPersistence.getPlayerByLoginName(uniqueLoginName);
    }
}
