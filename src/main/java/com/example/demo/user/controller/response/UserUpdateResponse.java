package com.example.demo.user.controller.response;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserUpdateResponse {
    private Long id;
    private String email;
    private String nickname;
    private String verificationCode;
    private UserStatus status;
    private Long lastLoginAt;

    public static UserUpdateResponse from(User user) {
        return UserUpdateResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .status(user.getStatus())
                .verificationCode(user.getVerificationCode())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }
}
