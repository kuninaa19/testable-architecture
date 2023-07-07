package com.example.demo.mock;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.service.port.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeUserRepository implements UserRepository {
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
        users.add(user);

        return user;
    }
}
