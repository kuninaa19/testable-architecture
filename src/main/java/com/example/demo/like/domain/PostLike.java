package com.example.demo.like.domain;

import com.example.demo.common.service.port.ClockHolder;
import com.example.demo.post.domain.Post;
import com.example.demo.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostLike extends Like {
    private final Post post;

    @Builder
    public PostLike(Long id, Long createdAt, Post post, User user) {
        super(id, createdAt, user);
        this.post = post;
    }

    public static Like from(Post post, User user, ClockHolder clockHolder) {
        return PostLike.builder()
                .createdAt(clockHolder.millis())
                .user(user)
                .post(post)
                .build();
    }
}
