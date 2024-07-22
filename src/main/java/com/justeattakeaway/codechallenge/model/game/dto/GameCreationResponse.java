package com.justeattakeaway.codechallenge.model.game.dto;

import com.justeattakeaway.codechallenge.model.game.InputType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameCreationResponse {

    private String id;
    private List<String> players = new ArrayList<>();
    private List<Integer> numbers;
    private String gameOwner;
    private InputType inputType;
    private String error;

}
