package com.example.demo.user.controller.port;

import com.example.demo.user.controller.request.CreateUserRequest;
import com.example.demo.user.controller.request.UpdateUserRequest;
import com.example.demo.user.controller.request.VerifyUserRequest;
import com.example.demo.user.domain.User;

public interface UserService {
    User getById(long id);

    User create(CreateUserRequest createUserRequest);

    void login(long id);

    void verify(long id, VerifyUserRequest verifyUserRequest);

    User update(long id, UpdateUserRequest updateUserRequest);
}