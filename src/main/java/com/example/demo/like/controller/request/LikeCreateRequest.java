package com.example.demo.like.controller.request;

import com.example.demo.like.domain.LikeType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LikeCreateRequest {
    private final long userId;
    private final LikeType likeType;

    @Builder
    public LikeCreateRequest(@JsonProperty("userId") long userId, @JsonProperty("likeType") LikeType likeType) {
        this.userId = userId;
        this.likeType = likeType;
    }
}
