package com.example.demo.user.controller.response;

import com.example.demo.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserResponse {
    private Long id;
    private String email;
    private String nickname;

    public static CreateUserResponse from(User user) {
        return CreateUserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }
}
