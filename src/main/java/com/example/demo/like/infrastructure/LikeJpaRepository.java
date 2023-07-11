package com.example.demo.like.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeJpaRepository extends JpaRepository<LikeEntity, Long> {
}
