package com.example.demo.like.service;

import com.example.demo.like.controller.port.LikeService;
import com.example.demo.like.controller.request.LikeCreateRequest;
import com.example.demo.like.domain.Like;
import com.example.demo.like.domain.LikeHandlerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeHandlerFactory likeHandlerFactory;

    @Override
    public Like like(long id, LikeCreateRequest likeCreateRequest) {
        LikeHandler likeHandler = likeHandlerFactory.createLikeHandler(likeCreateRequest);
        return likeHandler.handleLike(id, likeCreateRequest);
    }
}
