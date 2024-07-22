package com.justeattakeaway.codechallenge.controller;

import com.justeattakeaway.codechallenge.model.game.Game;
import com.justeattakeaway.codechallenge.model.game.dto.GameCreationRequest;
import com.justeattakeaway.codechallenge.model.game.dto.GameCreationResponse;
import com.justeattakeaway.codechallenge.model.game.dto.JoinGameRequest;
import com.justeattakeaway.codechallenge.model.game.dto.MoveResponse;
import com.justeattakeaway.codechallenge.model.game.dto.StartRequest;
import com.justeattakeaway.codechallenge.service.GameServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ScrooberGameController {

    private GameServiceImpl gameService;

    @Autowired
    ScrooberGameController(GameServiceImpl gameService) {
        this.gameService = gameService;

    }

    @PostMapping("/game/create")
    public ResponseEntity<GameCreationResponse> createGame(@RequestBody GameCreationRequest gameCreationRequest) {
        try {
            return new ResponseEntity<>(gameService.createGame(gameCreationRequest), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(GameCreationResponse.builder().error(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/getAllActiveGames")
    public ResponseEntity<List<Game>> findAllGames() {
        return new ResponseEntity<>(gameService.getAllActiveGames(), HttpStatus.OK);
    }

    @PostMapping("/joinGame/game/{gameId}")
    public ResponseEntity<Game> joinGame(@RequestBody JoinGameRequest joinGameRequest, @PathVariable String gameId) {
        return new ResponseEntity<>(gameService.joinGame(joinGameRequest, gameId), HttpStatus.OK);
    }

    @PostMapping("/start/game/{gameId}")
    public ResponseEntity<String> startGame(@PathVariable String gameId,
                                            @RequestParam(value = "number", required = false) Integer number,
                                            @RequestBody StartRequest startRequest) {
        Integer result;
        try {
            result = gameService.startGame(gameId, startRequest.getPlayerUniqueName(), number);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Game Started with Number: " + result, HttpStatus.OK);
    }

    @GetMapping("/makeMove/game/{gameId}/player/{playerId}")
    public ResponseEntity<MoveResponse> makeMove(@PathVariable String gameId,
                                                 @PathVariable String playerId,
                                                 @RequestParam(value = "addNumber", required = false) Integer addNumber) {
        try {
            MoveResponse moveResponse = gameService.makeMove(gameId, playerId, addNumber);
            return new ResponseEntity<>(moveResponse, HttpStatus.OK);
        } catch (RuntimeException e) {
            MoveResponse moveResponse = new MoveResponse();
            moveResponse.setError(e.getMessage());
            return new ResponseEntity<>(moveResponse, HttpStatus.BAD_REQUEST);
        }

    }

}
