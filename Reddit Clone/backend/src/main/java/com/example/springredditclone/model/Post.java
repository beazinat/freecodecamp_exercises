package com.example.springredditclone.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

// Those annotations are from Lombok, which is a Java library that helps to reduce boilerplate code by automatically generating getters, setters, constructors, and other common methods at compile time. The `@Entity` annotation indicates that this class is a JPA entity, which means it will be mapped to a database table. The `@Id` annotation specifies the primary key of the entity, and `@GeneratedValue` indicates that the value of the primary key will be generated automatically by the database. @AllArgsConstructor, @NoArgsConstructor, and @Builder are Lombok annotations that generate constructors and a builder pattern for creating instances of the class. 
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    // The `@Id` annotation indicates that this field is the primary key of the entity, and `@GeneratedValue` indicates that the value of the primary key will be generated automatically by the database. The variable `postId` will be used as the primary key. PostName, url, and description are fields that store the post's title, link, and content. The `voteCount` field stores the number of votes the post has received, initialized to 0. The `user` field is a reference to the user who created the post, and `createdDate` stores the timestamp of when the post was created. The `subreddit` field is a reference to the subreddit where the post belongs. We also use the annotations `@ManyToOne` and `@JoinColumn` to specify the relationships between entities, indicating that a post belongs to a user and a subreddit. @Lob is used to indicate that the `description` field can store large text data, which is useful for post content that may exceed the size of a standard string field.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    private String postName;
    private String url;
    @Lob
    private String description;
    private @Builder.Default Integer voteCount = 0;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
    private Instant createdDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Subreddit subreddit;
}
