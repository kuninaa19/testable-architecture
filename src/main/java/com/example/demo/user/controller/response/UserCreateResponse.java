package com.example.demo.user.controller.response;

import com.example.demo.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserCreateResponse {
    private Long id;
    private String email;
    private String nickname;

    public static UserCreateResponse from(User user) {
        return UserCreateResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }
}
