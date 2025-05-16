package com.example.springredditclone.service;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springredditclone.dto.PostRequest;
import com.example.springredditclone.dto.PostResponse;
import com.example.springredditclone.exceptions.PostNotFoundException;
import com.example.springredditclone.exceptions.SubredditNotFoundException;
import com.example.springredditclone.mapper.PostMapper;
import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.Subreddit;
import com.example.springredditclone.model.User;
import com.example.springredditclone.repository.PostRepository;
import com.example.springredditclone.repository.SubredditRepository;
import com.example.springredditclone.repository.UserRepository;
import com.example.springredditclone.security.service.AuthService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

        private final PostRepository postRepository;
        private final SubredditRepository subredditRepository;
        private final UserRepository userRepository;
        private final AuthService authService;
        private final PostMapper postMapper;

        public void save(PostRequest postRequest) {
                Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                                .orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
                postRepository.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));
        }

        @Transactional(readOnly = true)
        public PostResponse getPost(Long id) {
                Post post = postRepository.findById(id)
                                .orElseThrow(() -> new PostNotFoundException(id.toString()));
                return postMapper.mapToDto(post);
        }

        @Transactional(readOnly = true)
        public List<PostResponse> getAllPosts() {
                return postRepository.findAll()
                                .stream()
                                .map(postMapper::mapToDto)
                                .collect(Collectors.toList());
        }

        @Transactional(readOnly = true)
        public List<PostResponse> getPostsBySubreddit(Long subredditId) {
                Subreddit subreddit = subredditRepository.findById(subredditId)
                                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
                List<Post> posts = postRepository.findAllBySubreddit(subreddit);
                return posts.stream()
                                .map(postMapper::mapToDto)
                                .collect(Collectors.toList());
        }

        @Transactional(readOnly = true)
        public List<PostResponse> getPostsByUsername(String username) {
                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException(username));
                return postRepository.findByUser(user)
                                .stream()
                                .map(postMapper::mapToDto)
                                .collect(toList());
        }
}
