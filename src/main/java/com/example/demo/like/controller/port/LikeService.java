package com.example.demo.like.controller.port;

import com.example.demo.like.controller.request.LikeCreateRequest;
import com.example.demo.like.domain.Like;

public interface LikeService {
    Like like (long id, LikeCreateRequest likeCreateRequest);
}
