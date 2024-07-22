package com.justeattakeaway.codechallenge.model.game.dto;

import com.justeattakeaway.codechallenge.model.game.InputType;
import lombok.Data;

@Data
public class GameCreationRequest {
    private String loginName;
    private InputType inputType;
}
