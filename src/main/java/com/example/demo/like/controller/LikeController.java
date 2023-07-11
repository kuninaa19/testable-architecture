package com.example.demo.like.controller;

import com.example.demo.like.controller.port.LikeService;
import com.example.demo.like.domain.Like;
import com.example.demo.like.controller.request.LikeCreateRequest;
import com.example.demo.like.controller.response.LikeCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/posts/{id}/like")
    public ResponseEntity<LikeCreateResponse> like(@PathVariable long id, @RequestBody LikeCreateRequest likeCreateRequest) {
        Like like = likeService.like(id, likeCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(LikeCreateResponse.from(like));
    }
}
