package com.example.demo.like.domain;

import com.example.demo.user.domain.User;
import lombok.Getter;

@Getter
public abstract class Like {
    private final Long id;

    private final Long createdAt;

    private final User user;

    public Like(Long id, Long createdAt, User user) {
        this.id = id;
        this.createdAt = createdAt;
        this.user = user;
    }
}
