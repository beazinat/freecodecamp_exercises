package com.example.springredditclone.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

// Those annotations are from Lombok, which is a Java library that helps to reduce boilerplate code by automatically generating getters, setters, constructors, and other common methods at compile time. The `@Entity` annotation indicates that this class is a JPA entity, which means it will be mapped to a database table. The `@Id` annotation specifies the primary key of the entity, and `@GeneratedValue` indicates that the value of the primary key will be generated automatically by the database. @AllArgsConstructor, @NoArgsConstructor are Lombok annotations that generate constructors for creating instances of the class.
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
    // The `@Id` annotation indicates that this field is the primary key of the entity, and `@GeneratedValue` indicates that the value of the primary key will be generated automatically by the database. The variable `id` will be used as the primary key. The `text` field stores the content of the comment, and `createdDate` stores the timestamp of when the comment was created. The `post` field is a reference to the post to which this comment belongs, and the `user` field is a reference to the user who made the comment. We use the annotations `@ManyToOne` and `@JoinColumn` to specify the relationships between entities, indicating that a comment belongs to a post and a user.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    private Post post;
    private Instant createdDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
}
