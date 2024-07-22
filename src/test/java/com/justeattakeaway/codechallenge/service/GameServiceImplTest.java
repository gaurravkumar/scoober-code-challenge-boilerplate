package com.justeattakeaway.codechallenge.service;

import com.justeattakeaway.codechallenge.configuration.RabbitMQConfig;
import com.justeattakeaway.codechallenge.model.game.Game;
import com.justeattakeaway.codechallenge.model.game.GameProgress;
import com.justeattakeaway.codechallenge.model.game.InputType;
import com.justeattakeaway.codechallenge.model.game.dto.GameCreationRequest;
import com.justeattakeaway.codechallenge.model.game.dto.GameCreationResponse;
import com.justeattakeaway.codechallenge.model.game.dto.JoinGameRequest;
import com.justeattakeaway.codechallenge.model.game.dto.MoveResponse;
import com.justeattakeaway.codechallenge.model.player.Player;
import com.justeattakeaway.codechallenge.repository.GamePersistence;
import com.justeattakeaway.codechallenge.repository.GameProgressPersistence;
import com.justeattakeaway.codechallenge.service.common.ListenerService;
import com.justeattakeaway.codechallenge.service.common.MessageSenderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class GameServiceImplTest {

    @Mock
    private GamePersistence gamePersistence;

    @Mock
    private PlayerService playerService;

    @Mock
    private RabbitMQConfig rabbitMQConfig;

    @Mock
    private ListenerService listenerService;

    @Mock
    private MessageSenderService messageSenderService;

    @Mock
    private GameProgressPersistence gameProgressPersistence;

    @Mock
    private ProcessorImpl processor;

    @InjectMocks
    private GameServiceImpl gameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void startGame_gameNotFound() {
        when(gamePersistence.getGameById("1")).thenReturn(null);

        assertThrows(RuntimeException.class, () -> gameService.startGame("1", "john123", 10));
    }



    // Add more tests for the createGame method...

    @Test
    void getAllActiveGames() {
        Game game = new Game();
        game.setId("1");
        game.setGameOwner("john123");

        when(gamePersistence.findAllActiveGames()).thenReturn(Collections.singletonList(game));

        List<Game> games = gameService.getAllActiveGames();

        assertNotNull(games);
        assertEquals(1, games.size());
        assertEquals("john123", games.get(0).getGameOwner());
    }

    @Test
    void joinGame_gameNotFound() {
        JoinGameRequest request = new JoinGameRequest();
        request.setPlayer("john123");

        when(gamePersistence.findAllActiveGames()).thenReturn(Collections.emptyList());

        assertThrows(RuntimeException.class, () -> gameService.joinGame(request, "1"));
    }

    // Add more tests for the joinGame method...

    @Test
    void makeMove_gameOver() {
        when(gameProgressPersistence.getgameById("1")).thenReturn(null);

        assertThrows(RuntimeException.class, () -> gameService.makeMove("1", "john123", 1));
    }

    @Test
    void startGame_notGameOwner() {
        Game game = new Game();
        game.setGameOwner("john123");

        when(gamePersistence.getGameById("1")).thenReturn(game);

        assertThrows(RuntimeException.class, () -> gameService.startGame("1", "jane123", 10));
    }

    @Test
    void startGame_gameFinished() {
        Game game = new Game();
        game.setGameOwner("john123");
        game.setFinished(true);

        when(gamePersistence.getGameById("1")).thenReturn(game);

        assertThrows(RuntimeException.class, () -> gameService.startGame("1", "john123", 10));
    }

    @Test
    void createGame_uniqueLoginNameDoesNotExist() {
        GameCreationRequest request = new GameCreationRequest();
        request.setLoginName("john123");
        request.setInputType(InputType.AUTOMATIC);

        Player player = new Player();
        player.setUniqueLoginName("john123");

        when(playerService.getPlayerByUniqueLoginName("john123")).thenReturn(player);

        assertThrows(RuntimeException.class, () -> gameService.createGame(request));
    }

    @Test
    void joinGame_gameFound() {
        JoinGameRequest request = new JoinGameRequest();
        request.setPlayer("jane123");

        Game game = new Game();
        game.setId("1");
        game.setGameOwner("john123");
        game.setPlayers(new ArrayList<>(Arrays.asList("john123", "jane123")));

        when(gamePersistence.findAllActiveGames()).thenReturn((List.of(game)));
        when(gamePersistence.saveGame(any())).thenReturn(game);
        Game updatedGame = gameService.joinGame(request, "1");

        assertNotNull(updatedGame);
        assertEquals(2, updatedGame.getPlayers().size());
        assertTrue(updatedGame.getPlayers().contains("jane123"));
    }

}