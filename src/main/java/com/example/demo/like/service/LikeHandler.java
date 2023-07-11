package com.example.demo.like.service;

import com.example.demo.like.controller.request.LikeCreateRequest;
import com.example.demo.like.domain.Like;
import com.example.demo.like.domain.LikeType;

public interface LikeHandler {
    LikeType getLikeType();

    Like handleLike(long id, LikeCreateRequest likeCreateRequest);
}
