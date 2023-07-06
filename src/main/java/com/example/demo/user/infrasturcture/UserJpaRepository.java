package com.example.demo.user.infrasturcture;

import com.example.demo.user.domain.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByIdAndStatus(Long id, UserStatus status);
}
