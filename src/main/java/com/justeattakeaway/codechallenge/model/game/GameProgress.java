package com.justeattakeaway.codechallenge.model.game;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class GameProgress {
    @Id
    private String gameId;
    private String playerAndNumber;
}
