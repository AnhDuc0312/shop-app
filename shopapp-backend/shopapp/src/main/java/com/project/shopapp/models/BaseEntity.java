package com.project.shopapp.models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.*;
import lombok.experimental.StandardException;

import java.time.LocalDateTime;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity {

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updateAt;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
        updateAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate(){
        updateAt = LocalDateTime.now();
    }
}
