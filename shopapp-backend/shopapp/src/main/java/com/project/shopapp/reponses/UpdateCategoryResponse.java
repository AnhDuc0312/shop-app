package com.project.shopapp.reponses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryResponse {
    @JsonProperty("message")
    private String message;


}
