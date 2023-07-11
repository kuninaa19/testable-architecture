package com.example.demo.like.controller.response;

import com.example.demo.like.domain.Like;
import com.example.demo.user.controller.response.UserGetResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LikeCreateResponse {
    private Long id;
    private Long createdAt;
    private UserGetResponse user;

    public static LikeCreateResponse from(Like like) {
        return LikeCreateResponse.builder()
                .id(like.getId())
                .createdAt(like.getCreatedAt())
                .user(UserGetResponse.from(like.getUser()))
                .build();
    }
}
