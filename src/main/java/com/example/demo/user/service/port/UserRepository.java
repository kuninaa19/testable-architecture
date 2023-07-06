package com.example.demo.user.service.port;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByIdAndStatus(long id, UserStatus status);

    Optional<User> findById(long id);

    User save(User user);

    void delete(User user);
}
