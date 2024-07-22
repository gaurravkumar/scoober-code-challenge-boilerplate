package com.justeattakeaway.codechallenge.model.player;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Player {
    @Id
    private String id;
    private String name;
    private String uniqueLoginName;
    private String queueName;
}
