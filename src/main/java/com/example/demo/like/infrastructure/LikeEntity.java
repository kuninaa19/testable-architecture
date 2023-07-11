package com.example.demo.like.infrastructure;

import com.example.demo.like.domain.Like;
import com.example.demo.user.infrasturcture.UserEntity;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn()
@Table(name = "LIKES")
public abstract class LikeEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "created_at")
    protected Long createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    protected UserEntity user;

    public abstract Like toModel();
}
