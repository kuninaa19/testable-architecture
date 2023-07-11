package com.example.demo.like.domain;

import com.example.demo.like.controller.request.LikeCreateRequest;
import com.example.demo.like.service.LikeHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class LikeHandlerFactory {

    private final Map<LikeType, LikeHandler> likeHandlers;

    public LikeHandlerFactory(List<LikeHandler> likeHandlerList) {
        this.likeHandlers = likeHandlerList.stream()
                .collect(Collectors.toMap(LikeHandler::getLikeType, Function.identity()));
    }

    public LikeHandler createLikeHandler(LikeCreateRequest likeCreateRequest) {
        LikeType likeType = likeCreateRequest.getLikeType();
        LikeHandler likeHandler = likeHandlers.get(likeType);
        if (likeHandler == null) {
            throw new IllegalArgumentException("Unsupported LikeRequest type: " + likeType);
        }
        return likeHandler;
    }
}

