package com.justeattakeaway.codechallenge.repository;

import com.justeattakeaway.codechallenge.model.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Component
public class PlayerPersistence {

    private PlayerRepository repository;

    @Autowired
    public PlayerPersistence(PlayerRepository repository) {
        this.repository = repository;
    }

    public Player savePlayer(Player player) {
        return repository.save(player);
    }

    public Player getPlayerByLoginName(String uniqueLoginName) {
        return repository.findByUniqueLoginName(uniqueLoginName).orElse(null);
    }

}

@Repository
interface PlayerRepository extends MongoRepository<Player, String> {
    Optional<Player> findByUniqueLoginName(String uniqueLoginName);
}