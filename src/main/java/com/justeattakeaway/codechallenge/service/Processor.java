package com.justeattakeaway.codechallenge.service;

import com.justeattakeaway.codechallenge.model.game.Move;

public interface Processor {
    public Move processMessage(Integer message, Integer addNumber);
}
