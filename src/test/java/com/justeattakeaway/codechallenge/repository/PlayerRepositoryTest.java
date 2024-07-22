package com.justeattakeaway.codechallenge.repository;

import com.justeattakeaway.codechallenge.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PlayerPersistenceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerPersistence playerPersistence;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void savePlayer() {
        Player player = new Player();
        player.setUniqueLoginName("john123");

        when(playerRepository.save(player)).thenReturn(player);

        Player savedPlayer = playerPersistence.savePlayer(player);

        assertEquals("john123", savedPlayer.getUniqueLoginName());
        verify(playerRepository, times(1)).save(player);
    }

    @Test
    void getPlayerByLoginName_playerExists() {
        Player player = new Player();
        player.setUniqueLoginName("john123");

        when(playerRepository.findByUniqueLoginName("john123")).thenReturn(Optional.of(player));

        Player foundPlayer = playerPersistence.getPlayerByLoginName("john123");

        assertEquals("john123", foundPlayer.getUniqueLoginName());
        verify(playerRepository, times(1)).findByUniqueLoginName("john123");
    }

    @Test
    void getPlayerByLoginName_playerDoesNotExist() {
        when(playerRepository.findByUniqueLoginName("john123")).thenReturn(Optional.empty());

        Player foundPlayer = playerPersistence.getPlayerByLoginName("john123");

        assertNull(foundPlayer);
        verify(playerRepository, times(1)).findByUniqueLoginName("john123");
    }
}