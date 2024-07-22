package com.justeattakeaway.codechallenge.service;

import com.justeattakeaway.codechallenge.model.player.Player;
import com.justeattakeaway.codechallenge.model.player.dto.PlayerCreationRequest;
import com.justeattakeaway.codechallenge.model.player.dto.PlayerCreationResponse;

public interface PlayerService {
    PlayerCreationResponse createPlayer(PlayerCreationRequest playerCreationRequest);
    Player getPlayerByUniqueLoginName(String uniqueLoginName);
}
