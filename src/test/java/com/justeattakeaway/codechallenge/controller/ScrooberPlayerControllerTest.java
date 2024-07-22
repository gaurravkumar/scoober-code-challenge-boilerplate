package com.justeattakeaway.codechallenge.controller;

import com.justeattakeaway.codechallenge.model.player.dto.PlayerCreationRequest;
import com.justeattakeaway.codechallenge.model.player.dto.PlayerCreationResponse;
import com.justeattakeaway.codechallenge.service.PlayerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ScrooberPlayerControllerTest {

    @Mock
    private PlayerServiceImpl playerService;

    @InjectMocks
    private ScrooberPlayerController scrooberPlayerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPlayer() {
        PlayerCreationRequest request = new PlayerCreationRequest();
        request.setName("John");
        request.setUniqueLoginName("john123");

        PlayerCreationResponse response = new PlayerCreationResponse();
        response.setName("John");
        response.setUniqueLoginName("john123");

        when(playerService.createPlayer(request)).thenReturn(response);

        ResponseEntity<PlayerCreationResponse> result = scrooberPlayerController.createPlayer(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("John", result.getBody().getName());
        assertEquals("john123", result.getBody().getUniqueLoginName());
    }
}