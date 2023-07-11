package com.example.demo.post.infrastructure;

import com.example.demo.post.domain.Post;
import com.example.demo.user.infrasturcture.UserEntity;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

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

    @Basic(fetch = FetchType.LAZY)
    @Formula("(SELECT count(1) FROM LIKES AS l WHERE l.post_id = id)")
    private Long likeCount;

    public static PostEntity from(Post post) {
        PostEntity postEntity = new PostEntity();
        postEntity.id = post.getId();
        postEntity.title = post.getTitle();
        postEntity.text = post.getText();
        postEntity.createdAt = post.getCreatedAt();
        postEntity.updatedAt = post.getUpdatedAt();
        postEntity.user = UserEntity.from(post.getUser());
        postEntity.likeCount = post.getLikeCount();

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
                .likeCount(likeCount)
                .build();
    }
}
