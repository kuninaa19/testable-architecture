package com.example.demo.like.infrastructure;

import com.example.demo.like.domain.Like;
import com.example.demo.like.domain.PostLike;
import com.example.demo.post.infrastructure.PostEntity;
import com.example.demo.user.infrasturcture.UserEntity;

import javax.persistence.*;

@Entity
@DiscriminatorValue("P")
public class PostLikeEntity extends LikeEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    public static PostLikeEntity from(Like like) {
        PostLikeEntity likeEntity = new PostLikeEntity();
        likeEntity.id = like.getId();
        likeEntity.createdAt = like.getCreatedAt();
        likeEntity.post = PostEntity.from(((PostLike) like).getPost());
        likeEntity.user = UserEntity.from(like.getUser());

        return likeEntity;
    }

    public Like toModel() {
        return PostLike.builder()
                .id(id)
                .createdAt(createdAt)
                .post(post.toModel())
                .user(user.toModel())
                .build();
    }
}
