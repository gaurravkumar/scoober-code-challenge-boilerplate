package com.justeattakeaway.codechallenge.service;

import com.justeattakeaway.codechallenge.model.game.Move;
import org.springframework.stereotype.Component;

@Component
public class ProcessorImpl implements Processor {

    public Move processMessage(Integer message, Integer addNumber) {
        int number = message;
        Move move = new Move();

        if (addNumber == null) {
            int modifiedNumber = 0;
            if (number % 3 == 0) {
                modifiedNumber = number;
                move.setAdded(0);
            } else if (number % 3 == 1) {
                modifiedNumber = number - 1;
                move.setAdded(-1);
            } else if (number % 3 == 2) {
                modifiedNumber = number + 1;
                move.setAdded(1);
            }
            move.setResult(modifiedNumber);
        } else {
            move.setAdded(addNumber);
            move.setResult(number + addNumber);
        }

        if (Math.abs(move.getResult() - number) > 1 || move.getResult() % 3 != 0) {
            throw new RuntimeException("Move not Allowed. Make sure you passed 1, 0, -1 AND the result is divisible by 3.");
        }
        return move;
    }
}
