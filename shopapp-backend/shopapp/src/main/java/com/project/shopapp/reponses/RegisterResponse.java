package com.project.shopapp.reponses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopapp.models.User;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    @JsonProperty("message")
    private String message;

    @JsonProperty("user")
    private User user;

}
