package com.example.demo.mock;

import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.post.domain.Post;
import com.example.demo.post.service.port.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class FakePostRepository implements PostRepository {
    private final AtomicInteger atomicInteger = new AtomicInteger();
    private final List<Post> posts = new ArrayList<>();

    @Override
    public Post getById(long id) {
        return findById(id).orElseThrow(() -> new ResourceNotFoundException("post", id));
    }

    @Override
    public Optional<Post> findById(long id) {
        return posts.stream().filter(post -> post.getId().equals(id)).findFirst();
    }

    @Override
    public Post save(Post post) {
        posts.removeIf(item -> item.getId().equals(post.getId()));
        long id = atomicInteger.incrementAndGet();

        if (post.getId() != null) {
            posts.add(post);
            return post;
        }

        Post idAddedPost = Post.builder()
                .id(id)
                .title(post.getTitle())
                .text(post.getText())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .user(post.getUser())
                .likeCount(post.getLikeCount())
                .build();

        posts.add(idAddedPost);

        return idAddedPost;
    }
}