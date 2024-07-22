package com.justeattakeaway.codechallenge.model.game.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoveResponse {
    private int added;
    private int result;
    private String winner;
    private boolean finished;
    private String error;

}
