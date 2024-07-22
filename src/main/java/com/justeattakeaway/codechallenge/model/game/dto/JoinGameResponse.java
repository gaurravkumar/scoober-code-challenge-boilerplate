package com.justeattakeaway.codechallenge.model.game.dto;

import com.justeattakeaway.codechallenge.model.game.InputType;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class JoinGameResponse {
    private String id;
    private List<String> players;
    private List<Integer> numbers;
    private InputType inputType;
    private String gameOwner;
    private boolean finished;
}
