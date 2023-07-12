package com.example.demo.mock;

import com.example.demo.like.domain.Like;
import com.example.demo.like.domain.PostLike;
import com.example.demo.like.service.port.LikeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FakeLikeRepository implements LikeRepository {

    private final AtomicInteger atomicInteger = new AtomicInteger();
    private final List<Like> likes = new ArrayList<>();

    @Override
    public Like save(Like like) {
        likes.removeIf(item -> item.getId().equals(like.getId()));
        long id = atomicInteger.incrementAndGet();

        if (like.getId() != null) {
            likes.add(like);
            return like;
        }
        if (!(like instanceof PostLike)) {
            throw new RuntimeException("다형성을 확인해 주세요");
        }


        // FIXME Like 타입이 더 많아진다면 메서드 수정필요
        Like idAddedLike = PostLike.builder()
                .id(id)
                .post(((PostLike) like).getPost())
                .createdAt(like.getCreatedAt())
                .user(like.getUser())
                .build();

        likes.add(idAddedLike);
        return idAddedLike;
    }

    public long getCountByPostId(long postId) {
        return likes.stream().filter(like -> (((PostLike) like).getPost().getId().equals(postId))).count();
    }
}
