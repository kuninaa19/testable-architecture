package com.example.demo.post.service;

import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.common.service.port.ClockHolder;
import com.example.demo.post.controller.port.PostService;
import com.example.demo.post.controller.request.PostCreateRequest;
import com.example.demo.post.controller.request.PostUpdateRequest;
import com.example.demo.post.domain.Post;
import com.example.demo.post.service.port.PostRepository;
import com.example.demo.user.domain.User;
import com.example.demo.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ClockHolder clockHolder;

    public Post getById(long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", id));
    }

    @Transactional
    public Post create(PostCreateRequest postCreateRequest) {
        User writer = userRepository.getById(postCreateRequest.getWriterId());
        Post post = Post.from(postCreateRequest, writer, clockHolder);

        return postRepository.save(post);
    }

    @Transactional
    public Post update(PostUpdateRequest postUpdateRequest) throws IllegalAccessException {
        User writer = userRepository.getById(postUpdateRequest.getWriterId());
        Post post = getById(postUpdateRequest.getId());
        post = post.update(postUpdateRequest, writer, clockHolder);

        return postRepository.save(post);
    }
}
