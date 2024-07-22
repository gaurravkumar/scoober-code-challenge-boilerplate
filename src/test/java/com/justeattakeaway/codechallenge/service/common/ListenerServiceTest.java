package com.justeattakeaway.codechallenge.service.common;

import com.justeattakeaway.codechallenge.model.game.GameProgress;
import com.justeattakeaway.codechallenge.repository.GameProgressPersistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ListenerServiceTest {

    @Mock
    private ConnectionFactory connectionFactory;

    @Mock
    private GameProgressPersistence gameProgressPersistence;

    @InjectMocks
    private ListenerService listenerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listenToQueue() {
        String queueName = "myQueue";
        String gameId = "1";
        String playerId = "john123";

        GameProgress gameProgress = new GameProgress();
        gameProgress.setGameId(gameId);
        gameProgress.setPlayerAndNumber(playerId + ":10");

        when(gameProgressPersistence.getgameById(gameId)).thenReturn(gameProgress);

        listenerService.listenToQueue(queueName, gameId, playerId);

        verify(connectionFactory, times(2)).createConnection();
        verify(gameProgressPersistence, times(0)).getgameById(gameId);
        verify(gameProgressPersistence, times(0)).saveGame(any(GameProgress.class));
    }
}