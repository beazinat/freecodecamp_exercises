package com.example.springredditclone.model;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Those annotations are from Lombok, which is a Java library that helps to reduce boilerplate code by automatically generating getters, setters, constructors, and other common methods at compile time. The `@Entity` annotation indicates that this class is a JPA entity, which means it will be mapped to a database table. The `@Id` annotation specifies the primary key of the entity, and `@GeneratedValue` indicates that the value of the primary key will be generated automatically by the database. @AllArgsConstructor, @NoArgsConstructor, and @Builder are Lombok annotations that generate constructors and a builder pattern for creating instances of the class.
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Subreddit {
    // The `@Id` annotation indicates that this field is the primary key of the entity, and `@GeneratedValue` indicates that the value of the primary key will be generated automatically by the database. We also use the annotations `@OneToMany` and `@ManyToOne` to specify the relationships between entities (one subreddit can have many posts, and one user can create many subreddits). The variable `id` will be used as the primary key. Name and description are fields that store the subreddit information. The `createdDate` field stores the timestamp of when the subreddit was created, and `user` is a reference to the user who created the subreddit. The `posts` field is a list of posts associated with the subreddit.
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private String description;
    @OneToMany(fetch = LAZY)
    private List<Post> posts;
    private Instant createdDate;
    @ManyToOne(fetch = LAZY)
    private User user;
}