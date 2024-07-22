package com.justeattakeaway.codechallenge.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.justeattakeaway.codechallenge.controller.ScrooberGameController;
import com.justeattakeaway.codechallenge.model.game.Game;
import com.justeattakeaway.codechallenge.model.game.InputType;
import com.justeattakeaway.codechallenge.model.game.dto.*;
import com.justeattakeaway.codechallenge.model.player.Player;
import com.justeattakeaway.codechallenge.service.GameServiceImpl;
import com.justeattakeaway.codechallenge.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ScrooberGameControllerTest {

    @Mock
    private GameServiceImpl gameService;

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private ScrooberGameController scrooberGameController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(scrooberGameController).build();
    }

    @Test
    void findAllGames() throws Exception {
        ReadGameResponse game = ReadGameResponse.builder()
                .id("1")
                .gameOwner("john123").build();

        when(gameService.getAllActiveGames()).thenReturn(Collections.singletonList(game));

        mockMvc.perform(get("/getAllActiveGames"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].gameOwner").value("john123"));
    }

    @Test
    void joinGame() throws Exception {
        JoinGameRequest request = new JoinGameRequest();
        request.setPlayer("john123");

        JoinGameResponse game = JoinGameResponse.builder()
                .id("1")
                .gameOwner("john123")
                .build();


        when(gameService.joinGame(request, "1")).thenReturn(game);

        mockMvc.perform(post("/joinGame/game/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"player\":\"john123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.gameOwner").value("john123"));
    }

    @Test
    void startGame() throws Exception {
        StartRequest request = new StartRequest();
        request.setPlayerUniqueName("john123");

        when(gameService.startGame("1", "john123", 10)).thenReturn(10);

        mockMvc.perform(post("/start/game/1?number=10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"playerUniqueName\":\"john123\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Game Started with Number: 10"));
    }

    @Test
    void makeMove() throws Exception {
        MoveResponse response = new MoveResponse();
        response.setResult(10);
        response.setAdded(1);

        when(gameService.makeMove("1", "john123", 1)).thenReturn(response);

        mockMvc.perform(get("/makeMove/game/1/player/john123?addNumber=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(10))
                .andExpect(jsonPath("$.added").value(1));
    }
}