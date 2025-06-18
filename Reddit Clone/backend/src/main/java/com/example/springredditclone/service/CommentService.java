package com.example.springredditclone.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springredditclone.dto.CommentsDto;
import com.example.springredditclone.dto.NotificationEmail;
import com.example.springredditclone.exceptions.PostNotFoundException;
import com.example.springredditclone.exceptions.SpringRedditException;
import com.example.springredditclone.mapper.CommentMapper;
import com.example.springredditclone.model.Comment;
import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.User;
import com.example.springredditclone.repository.CommentRepository;
import com.example.springredditclone.repository.PostRepository;
import com.example.springredditclone.repository.UserRepository;
import com.example.springredditclone.security.service.AuthService;

import lombok.AllArgsConstructor;

// This class is responsible for managing comments on posts in the Reddit clone application. It provides methods to save a comment, retrieve all comments for a specific post or user, and check for inappropriate language in comments. The service uses repositories to interact with the database and employs a mapper to convert between DTOs and entity models. It also sends email notifications to users when their posts receive comments. The critical part of this service is the `save` method, which maps the incoming `CommentsDto` to a `Comment` entity, associates it with the post and the current user, and saves it to the database. This encapsulates the core functionality of adding comments to posts in the application.
@Service
@AllArgsConstructor
public class CommentService {
    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void save(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post: " + POST_URL);
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " commented on your post", user.getEmail(), message));
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).toList();
    }

    public List<CommentsDto> getAllCommentsForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new PostNotFoundException(username));
        return commentRepository.findByUser(user)
                .stream()
                .map(commentMapper::mapToDto).toList();
    }

    public boolean containsSwearWords(String comment) {
        if (comment.contains("shit")) {
            throw new SpringRedditException("Comments contains unnacceptable language");
        }
        return false;
    }
}
