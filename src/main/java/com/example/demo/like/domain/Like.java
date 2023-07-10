package com.example.demo.like.domain;

import com.example.demo.common.service.port.ClockHolder;
import com.example.demo.post.domain.Post;
import com.example.demo.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Like {
    private final Long id;

    private final Long createdAt;

    private final Post post;

    private final User user;

    @Builder
    public Like(Long id, Long createdAt, Post post, User user) {
        this.id = id;
        this.createdAt = createdAt;
        this.post = post;
        this.user = user;
    }

    public static Like from(Post post, User user, ClockHolder clockHolder) {
        return Like.builder()
                .createdAt(clockHolder.millis())
                .post(post)
                .user(user)
                .build();
    }
}
