package com.example.demo.like.service.port;

import com.example.demo.like.domain.Like;

public interface LikeRepository {
    Like save(Like like);
}