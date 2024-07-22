package com.justeattakeaway.codechallenge.repository;

import com.justeattakeaway.codechallenge.model.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GamePersistenceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GamePersistence gamePersistence;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveGame() {
        Game game = new Game();
        game.setId("1");

        when(gameRepository.save(game)).thenReturn(game);

        Game savedGame = gamePersistence.saveGame(game);

        assertEquals("1", savedGame.getId());
        verify(gameRepository, times(1)).save(game);
    }

    @Test
    void findAllActiveGames() {
        Game game1 = new Game();
        game1.setId("1");
        game1.setFinished(false);

        Game game2 = new Game();
        game2.setId("2");
        game2.setFinished(false);

        when(gameRepository.findByFinishedFalse()).thenReturn(Arrays.asList(game1, game2));

        List<Game> games = gamePersistence.findAllActiveGames();

        assertEquals(2, games.size());
        verify(gameRepository, times(1)).findByFinishedFalse();
    }

    @Test
    void getGameById_gameExists() {
        Game game = new Game();
        game.setId("1");

        when(gameRepository.findById("1")).thenReturn(Optional.of(game));

        Game foundGame = gamePersistence.getGameById("1");

        assertEquals("1", foundGame.getId());
        verify(gameRepository, times(1)).findById("1");
    }

    @Test
    void getGameById_gameDoesNotExist() {
        when(gameRepository.findById("1")).thenReturn(Optional.empty());

        Game foundGame = gamePersistence.getGameById("1");

        assertNull(foundGame);
        verify(gameRepository, times(1)).findById("1");
    }
}