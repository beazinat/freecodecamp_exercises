package com.example.springredditclone.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

// Those annotations are from Lombok, which is a Java library that helps to reduce boilerplate code by automatically generating getters, setters, constructors, and other common methods at compile time. The `@Entity` annotation indicates that this class is a JPA entity, which means it will be mapped to a database table. The `@Id` annotation specifies the primary key of the entity, and `@GeneratedValue` indicates that the value of the primary key will be generated automatically by the database. @AllArgsConstructor, @NoArgsConstructor, and @Builder are Lombok annotations that generate constructors and a builder pattern for creating instances of the class.
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    // The `@Id` annotation indicates that this field is the primary key of the entity, and `@GeneratedValue` indicates that the value of the primary key will be generated automatically by the database. The variable `userId` will be used as the primary key. Username, password, and email are fields that store the user's credentials and contact information. The `created` field stores the timestamp of when the user was created, and `enabled` is a boolean flag indicating whether the user account is active or not.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String password;
    private String email;
    private Instant created;
    private boolean enabled;
}
