package com.justeattakeaway.codechallenge.controller;

import com.justeattakeaway.codechallenge.model.player.dto.PlayerCreationRequest;
import com.justeattakeaway.codechallenge.model.player.dto.PlayerCreationResponse;
import com.justeattakeaway.codechallenge.service.PlayerServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScrooberPlayerController {

    PlayerServiceImpl playerService;

    ScrooberPlayerController(PlayerServiceImpl playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/player/create")
    public ResponseEntity<PlayerCreationResponse> createPlayer(@RequestBody PlayerCreationRequest playerCreationRequest) {
        return new ResponseEntity<>(playerService.createPlayer(playerCreationRequest), HttpStatus.OK);
    }

}
