package com.justeattakeaway.codechallenge.service.common;

import com.justeattakeaway.codechallenge.model.game.GameProgress;
import com.justeattakeaway.codechallenge.repository.GameProgressPersistence;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListenerService {

    private ConnectionFactory connectionFactory;
    private GameProgressPersistence gameProgressPersistence;

    @Autowired
    public ListenerService(ConnectionFactory connectionFactory, GameProgressPersistence gameProgressPersistence) {
        this.connectionFactory = connectionFactory;
        this.gameProgressPersistence = gameProgressPersistence;
    }

    public void listenToQueue(String queueName, String gameId, String playerId) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMissingQueuesFatal(false);
        container.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                System.out.println("Received message: " + new String(message.getBody()));
                var gameProgress = gameProgressPersistence.getgameById(gameId);
                if (gameProgress == null) {
                    gameProgress = new GameProgress();
                    gameProgress.setGameId(gameId);
                    gameProgress.setPlayerAndNumber(playerId + ":" + Integer.valueOf(new String(message.getBody()).replaceAll("\"", "")));
                } else {
                    var result = String.join(",", gameProgress.getPlayerAndNumber(), playerId + ":" + Integer.valueOf(new String(message.getBody()).replaceAll("\"", "")));
                    gameProgress.setPlayerAndNumber(result);
                }
                gameProgressPersistence.saveGame(gameProgress);
            }
        });
        container.start();
    }
}
