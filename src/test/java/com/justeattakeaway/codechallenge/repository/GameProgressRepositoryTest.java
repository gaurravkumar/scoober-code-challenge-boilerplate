package com.justeattakeaway.codechallenge.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.justeattakeaway.codechallenge.model.game.GameProgress;
import com.justeattakeaway.codechallenge.repository.GameProgressPersistence;
import com.justeattakeaway.codechallenge.repository.GameProgressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class GameProgressPersistenceTest {

    @Mock
    private GameProgressRepository gameProgressRepository;

    @InjectMocks
    private GameProgressPersistence gameProgressPersistence;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveGame() {
        GameProgress gameProgress = new GameProgress();
        gameProgress.setGameId("1");

        when(gameProgressRepository.save(gameProgress)).thenReturn(gameProgress);

        GameProgress savedGameProgress = gameProgressPersistence.saveGame(gameProgress);

        assertEquals("1", savedGameProgress.getGameId());
        verify(gameProgressRepository, times(1)).save(gameProgress);
    }

    @Test
    void getgameById_gameExists() {
        GameProgress gameProgress = new GameProgress();
        gameProgress.setGameId("1");

        when(gameProgressRepository.findById("1")).thenReturn(Optional.of(gameProgress));

        GameProgress foundGameProgress = gameProgressPersistence.getgameById("1");

        assertEquals("1", foundGameProgress.getGameId());
        verify(gameProgressRepository, times(1)).findById("1");
    }

    @Test
    void getgameById_gameDoesNotExist() {
        when(gameProgressRepository.findById("1")).thenReturn(Optional.empty());

        GameProgress foundGameProgress = gameProgressPersistence.getgameById("1");

        assertNull(foundGameProgress);
        verify(gameProgressRepository, times(1)).findById("1");
    }
}