package com.example.demo.like.service;

import com.example.demo.common.service.port.ClockHolder;
import com.example.demo.like.controller.request.LikeCreateRequest;
import com.example.demo.like.domain.Like;
import com.example.demo.like.domain.LikeType;
import com.example.demo.like.domain.PostLike;
import com.example.demo.like.service.port.LikeRepository;
import com.example.demo.post.domain.Post;
import com.example.demo.post.service.port.PostRepository;
import com.example.demo.user.domain.User;
import com.example.demo.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PostLikeHandler implements LikeHandler {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final ClockHolder clockHolder;

    @Override
    public LikeType getLikeType() {
        return LikeType.P;
    }

    @Override
    public Like handleLike(long id, LikeCreateRequest likeCreateRequest) {
        Post post = postRepository.getById(id);
        User user = userRepository.getById(likeCreateRequest.getUserId());
        Like like = PostLike.from(post, user, clockHolder);

        return likeRepository.save(like);
    }
}

