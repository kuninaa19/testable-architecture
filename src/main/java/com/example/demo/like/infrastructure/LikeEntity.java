package com.example.demo.like.infrastructure;

import com.example.demo.like.domain.Like;
import com.example.demo.post.infrastructure.PostEntity;
import com.example.demo.user.infrasturcture.UserEntity;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn()
@Table(name = "LIKES")
public class LikeEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private Long createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public static LikeEntity from(Like like) {
        LikeEntity likeEntity = new LikeEntity();
        likeEntity.id = like.getId();
        likeEntity.createdAt = like.getCreatedAt();
        likeEntity.post = PostEntity.from(like.getPost());
        likeEntity.user = UserEntity.from(like.getUser());

        return likeEntity;
    }

    public Like toModel() {
        return Like.builder()
                .id(id)
                .createdAt(createdAt)
                .post(post.toModel())
                .user(user.toModel())
                .build();
    }
}
