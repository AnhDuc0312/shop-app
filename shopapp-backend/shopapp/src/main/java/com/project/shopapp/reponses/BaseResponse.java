package com.project.shopapp.reponses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Data

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {


    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updateAt;
}
