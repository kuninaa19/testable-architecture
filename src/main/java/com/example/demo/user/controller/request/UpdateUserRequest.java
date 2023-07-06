package com.example.demo.user.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateUserRequest {
    private final String email;
    private final String nickname;

    @Builder
    public UpdateUserRequest(@JsonProperty("email") String email, @JsonProperty("nickname") String nickname) {
        this.email = email;
        this.nickname = nickname;
    }
}
