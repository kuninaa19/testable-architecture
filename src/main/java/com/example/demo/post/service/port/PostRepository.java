package com.example.demo.post.service.port;

import com.example.demo.post.domain.Post;

import java.util.Optional;

public interface PostRepository {
    Post getById(long id);

    Optional<Post> findById(long id);

    Post save(Post post);
}
