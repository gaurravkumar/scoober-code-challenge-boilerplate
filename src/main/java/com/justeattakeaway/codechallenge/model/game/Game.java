package com.justeattakeaway.codechallenge.model.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Game {

    @Id
    private String id;
    private List<String> players = new ArrayList<>();
    private List<Integer> numbers;
    private InputType inputType;
    private String gameOwner;
    private boolean finished;

}
