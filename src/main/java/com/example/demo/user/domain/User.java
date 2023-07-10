package com.example.demo.user.domain;

import com.example.demo.common.service.port.ClockHolder;
import com.example.demo.common.service.port.UuidHolder;
import com.example.demo.user.controller.request.UserCreateRequest;
import com.example.demo.user.controller.request.UserUpdateRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

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
                .status(status)
                .lastLoginAt(clockHolder.millis())
                .build();
    }

    public User update(UserUpdateRequest userUpdateRequest) {
        return User.builder()
                .id(id)
                .email(userUpdateRequest.getEmail())
                .nickname(userUpdateRequest.getNickname())
                .verificationCode(verificationCode)
                .status(status)
                .lastLoginAt(lastLoginAt)
                .build();
    }

    public static User from(UserCreateRequest userCreateRequest, UuidHolder uuidHolder) {
        return User.builder()
                .email(userCreateRequest.getEmail())
                .nickname(userCreateRequest.getNickname())
                .verificationCode(uuidHolder.random())
                .status(UserStatus.UNCERTIFIED)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email) && Objects.equals(nickname, user.nickname) && Objects.equals(verificationCode, user.verificationCode) && status == user.status && Objects.equals(lastLoginAt, user.lastLoginAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, nickname, verificationCode, status, lastLoginAt);
    }
}