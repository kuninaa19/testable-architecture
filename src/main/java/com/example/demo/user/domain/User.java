package com.example.demo.user.domain;

import com.example.demo.common.service.port.ClockHolder;
import com.example.demo.common.service.port.UuidHolder;
import com.example.demo.user.controller.request.CreateUserRequest;
import com.example.demo.user.controller.request.UpdateUserRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
public class User {
    private final Long id;
    private final String email;
    private final String nickname;
    private final String verificationCode;
    private final UserStatus status;
    private final Long lastLoginAt;

    @Builder
    public User(Long id, String email, String nickname, String verificationCode, UserStatus status, Long lastLoginAt) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.verificationCode = verificationCode;
        this.status = status;
        this.lastLoginAt = lastLoginAt;
    }

    public User verify(String verificationCode) {
        if (!this.verificationCode.equals(verificationCode)) {
            throw new IllegalArgumentException("동일하지않은 인증코드 입니다.");
        }

        return User.builder()
                .id(id)
                .email(email)
                .nickname(nickname)
                .verificationCode(verificationCode)
                .status(UserStatus.ACTIVATION)
                .lastLoginAt(lastLoginAt)
                .build();
    }

    public User login(ClockHolder clockHolder) {
        return User.builder()
                .id(id)
                .email(email)
                .nickname(nickname)
                .verificationCode(verificationCode)
                .status(UserStatus.ACTIVATION)
                .lastLoginAt(clockHolder.millis())
                .build();
    }

    public User update(UpdateUserRequest updateUserRequest) {
        return User.builder()
                .id(id)
                .email(updateUserRequest.getEmail())
                .nickname(updateUserRequest.getNickname())
                .verificationCode(verificationCode)
                .status(status)
                .lastLoginAt(lastLoginAt)
                .build();
    }

    public static User from(CreateUserRequest createUserRequest, UuidHolder uuidHolder) {
        return User.builder()
                .email(createUserRequest.getEmail())
                .nickname(createUserRequest.getNickname())
                .verificationCode(uuidHolder.random())
                .status(UserStatus.UNCERTIFIED)
                .build();
    }
}