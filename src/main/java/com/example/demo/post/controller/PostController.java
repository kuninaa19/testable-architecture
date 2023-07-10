package com.example.demo.post.controller;

import com.example.demo.post.controller.port.PostService;
import com.example.demo.post.controller.request.PostCreateRequest;
import com.example.demo.post.controller.request.PostUpdateRequest;
import com.example.demo.post.controller.response.PostCreateResponse;
import com.example.demo.post.controller.response.PostGetResponse;
import com.example.demo.post.controller.response.PostUpdateResponse;
import com.example.demo.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<PostGetResponse> getById(@PathVariable long id) {
        Post post = postService.getById(id);

        return ResponseEntity.ok().body(PostGetResponse.from(post));
    }

    @PostMapping("")
    public ResponseEntity<PostCreateResponse> create(@RequestBody PostCreateRequest postCreateRequest) {
        Post post = postService.create(postCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(PostCreateResponse.from(post));
    }

    @PutMapping("")
    public ResponseEntity<PostUpdateResponse> update(@RequestBody PostUpdateRequest postUpdateRequest) throws IllegalAccessException {
        Post post = postService.update(postUpdateRequest);

        return ResponseEntity.ok().body(PostUpdateResponse.from(post));
    }
}
