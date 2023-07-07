package com.example.demo.mock;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.service.port.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class FakeUserRepository implements UserRepository {
    private final AtomicInteger atomicInteger = new AtomicInteger();
    private final List<User> users = new ArrayList<>();

    @Override
    public Optional<User> findByIdAndStatus(long id, UserStatus status) {
        return users.stream().filter(item -> item.getId().equals(id) && item.getStatus().equals(UserStatus.ACTIVATION)).findFirst();
    }

    @Override
    public Optional<User> findById(long id) {
        return users.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    @Override
    public User save(User user) {
        users.removeIf(item -> item.getId().equals(user.getId()));
        long id = atomicInteger.incrementAndGet();

        if (user.getId() != null) {
            users.add(user);
            return user;
        }

        User idAddedUser = User.builder()
                .id(id)
                .email(user.getEmail())
                .nickname(user.getNickname())
                .lastLoginAt(user.getLastLoginAt())
                .verificationCode(user.getVerificationCode())
                .status(user.getStatus())
                .build();

        users.add(idAddedUser);

        return idAddedUser;
    }
}
