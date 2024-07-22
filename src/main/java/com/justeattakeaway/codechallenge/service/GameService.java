package com.justeattakeaway.codechallenge.service;

import com.justeattakeaway.codechallenge.model.game.Game;
import com.justeattakeaway.codechallenge.model.game.dto.GameCreationRequest;
import com.justeattakeaway.codechallenge.model.game.dto.GameCreationResponse;
import com.justeattakeaway.codechallenge.model.game.dto.JoinGameRequest;
import com.justeattakeaway.codechallenge.model.game.dto.JoinGameResponse;
import com.justeattakeaway.codechallenge.model.game.dto.MoveResponse;
import com.justeattakeaway.codechallenge.model.game.dto.ReadGameResponse;

import java.util.List;

public interface GameService {
    // Add method signature from GameServiceImpl.java
    public GameCreationResponse createGame(GameCreationRequest gameCreationRequest);
    public List<ReadGameResponse> getAllActiveGames();
    public JoinGameResponse joinGame(JoinGameRequest joinGameRequest, String gameId);
    public Integer startGame(String gameId, String playerUniqueName, Integer number);
    public MoveResponse makeMove(String gameId, String playerId, Integer number);

}
