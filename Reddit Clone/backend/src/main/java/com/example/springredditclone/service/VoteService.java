package com.example.springredditclone.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.springredditclone.dto.VoteDto;
import com.example.springredditclone.exceptions.PostNotFoundException;
import com.example.springredditclone.exceptions.SpringRedditException;
import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.Vote;
import com.example.springredditclone.model.VoteType;
import com.example.springredditclone.repository.PostRepository;
import com.example.springredditclone.repository.VoteRepository;
import com.example.springredditclone.security.service.AuthService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post not found with id - " + voteDto.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
                authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
            throw new SpringRedditException("You have already " + voteDto.getVoteType() + "d for this post");
        }
        if (VoteType.UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
