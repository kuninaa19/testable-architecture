package com.example.demo.like.infrastructure;

import com.example.demo.like.domain.Like;
import com.example.demo.like.domain.PostLike;
import com.example.demo.like.service.port.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepository {
    private final LikeJpaRepository likeJpaRepository;

    @Override
    public Like save(Like like) {
        if (like instanceof PostLike) {
            return likeJpaRepository.save(PostLikeEntity.from(like)).toModel();
        }

        throw new IllegalArgumentException("Unsupported like type: " + like.getClass());
    }
}
