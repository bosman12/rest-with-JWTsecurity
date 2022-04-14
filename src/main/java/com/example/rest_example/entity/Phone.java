package com.example.rest_example.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "phone")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id",nullable = false)
    private User userPhones;
}
