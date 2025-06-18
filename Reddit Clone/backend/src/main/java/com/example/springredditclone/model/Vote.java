package com.example.springredditclone.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

// Those annotations are from Lombok, which is a Java library that helps to reduce boilerplate code by automatically generating getters, setters, constructors, and other common methods at compile time. The `@Entity` annotation indicates that this class is a JPA entity, which means it will be mapped to a database table. The `@Id` annotation specifies the primary key of the entity, and `@GeneratedValue` indicates that the value of the primary key will be generated automatically by the database. @AllArgsConstructor, @NoArgsConstructor, and @Builder are Lombok annotations that generate constructors and a builder pattern for creating instances of the class.
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Vote {
    // The `@Id` annotation indicates that this field is the primary key of the entity, and `@GeneratedValue` indicates that the value of the primary key will be generated automatically by the database. @NotNull is a validation annotation that ensures that the `voteType` field is not null. @JoinColumn and @ManyToOne are annotations from JPA that are used to define the relationship between the `Vote` entity and other entities (in this case, we need many posts and users to one vote). The variable `voteId` will be used as the primary key. The `voteType` field stores the type of vote (upvote or downvote). The `post` field is a reference to the post that is being voted on, and the `user` field is a reference to the user who cast the vote.
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long voteId;
    private VoteType voteType;
    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    private Post post;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
}
