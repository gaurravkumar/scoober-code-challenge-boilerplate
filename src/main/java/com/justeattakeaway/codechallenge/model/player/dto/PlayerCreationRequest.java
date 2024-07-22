package com.justeattakeaway.codechallenge.model.player.dto;

import lombok.Data;

@Data
public class PlayerCreationRequest {
    private String name;
    private String uniqueLoginName;
}
