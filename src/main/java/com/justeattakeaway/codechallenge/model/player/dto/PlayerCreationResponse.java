package com.justeattakeaway.codechallenge.model.player.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerCreationResponse {
    private String id;
    private String name;
    private String uniqueLoginName;
    private String queueName;
    private String errrorMessage;
}
