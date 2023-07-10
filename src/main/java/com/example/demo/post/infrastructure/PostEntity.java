package com.example.demo.post.infrastructure;

import com.example.demo.like.infrastructure.LikeEntity;
import com.example.demo.post.domain.Post;
import com.example.demo.user.infrasturcture.UserEntity;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "POST")
public class PostEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<LikeEntity> likes;

    public static PostEntity from(Post post) {
        PostEntity postEntity = new PostEntity();
        postEntity.id = post.getId();
        postEntity.title = post.getTitle();
        postEntity.text = post.getText();
        postEntity.createdAt = post.getCreatedAt();
        postEntity.updatedAt = post.getUpdatedAt();
        postEntity.user = UserEntity.from(post.getUser());
        postEntity.likes = post.getLikes().stream().map(LikeEntity::from).collect(Collectors.toList());

        return postEntity;
    }

    public Post toModel() {
        return Post.builder()
                .id(id)
                .title(title)
                .text(text)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .user(user.toModel())
                .likes(likes.stream().map(LikeEntity::toModel).collect(Collectors.toList()))
                .build();
    }
}
