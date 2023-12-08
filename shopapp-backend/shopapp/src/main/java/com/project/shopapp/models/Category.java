package com.project.shopapp.models;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;
}
