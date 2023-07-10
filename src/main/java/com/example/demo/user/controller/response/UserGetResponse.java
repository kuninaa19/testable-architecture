package com.example.demo.user.controller.response;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserGetResponse {
    private Long id;
    private String email;
    private String nickname;
    private UserStatus status;
    private Long lastLoginAt;

    public static UserGetResponse from(User user) {
        return UserGetResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .status(user.getStatus())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }
}
