package com.example.demo.post.domain;

import com.example.demo.common.service.port.ClockHolder;
import com.example.demo.like.domain.Like;
import com.example.demo.post.controller.request.PostCreateRequest;
import com.example.demo.post.controller.request.PostUpdateRequest;
import com.example.demo.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Post {
    private final Long id;

    private final String title;

    private final String text;

    private final Long createdAt;

    private final Long updatedAt;

    private final User user;

    private final List<Like> likes;

    @Builder
    public Post(Long id, String title, String text, Long createdAt, Long updatedAt, User user, List<Like> likes) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
        this.likes = likes;
    }

    public static Post from(PostCreateRequest postCreateRequest, User writer, ClockHolder clockHolder) {
        return Post.builder()
                .title(postCreateRequest.getTitle())
                .text(postCreateRequest.getText())
                .user(writer)
                .createdAt(clockHolder.millis())
                .likes(new ArrayList<>())
                .build();
    }

    public Post update(PostUpdateRequest postUpdateRequest, User writer, ClockHolder clockHolder) throws IllegalAccessException {
        if(!user.equals(writer)){
            throw new IllegalAccessException("작성자가 아닙니다");
        }

        return Post.builder()
                .id(id)
                .title(postUpdateRequest.getTitle())
                .text(postUpdateRequest.getText())
                .createdAt(createdAt)
                .updatedAt(clockHolder.millis())
                .user(user)
                .likes(likes)
                .build();
    }
}
