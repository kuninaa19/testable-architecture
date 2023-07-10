package com.example.demo.user.controller.port;

import com.example.demo.user.controller.request.UserCreateRequest;
import com.example.demo.user.controller.request.UserUpdateRequest;
import com.example.demo.user.controller.request.UserVerifyRequest;
import com.example.demo.user.domain.User;

public interface UserService {
    User getById(long id);

    User create(UserCreateRequest userCreateRequest);

    void login(long id);

    void verify(long id, UserVerifyRequest userVerifyRequest);

    User update(long id, UserUpdateRequest userUpdateRequest);
}