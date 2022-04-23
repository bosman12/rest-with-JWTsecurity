package com.example.rest_example.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id",nullable = false)
    private User userProducts;

}
