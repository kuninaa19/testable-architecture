package com.example.demo.user.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserUpdateRequest {
    private final String email;
    private final String nickname;

    @Builder
    public UserUpdateRequest(@JsonProperty("email") String email, @JsonProperty("nickname") String nickname) {
        this.email = email;
        this.nickname = nickname;
    }
}
