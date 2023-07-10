package com.example.demo.post.controller.port;

import com.example.demo.post.controller.request.PostCreateRequest;
import com.example.demo.post.controller.request.PostUpdateRequest;
import com.example.demo.post.domain.Post;

public interface PostService {
    Post getById(long id);

    Post create(PostCreateRequest postCreateRequest);

    Post update(PostUpdateRequest postUpdateRequest) throws IllegalAccessException;
}
