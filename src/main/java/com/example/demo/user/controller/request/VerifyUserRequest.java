package com.example.demo.user.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class VerifyUserRequest {
    private final String verificationCode;

    @Builder
    public VerifyUserRequest(@JsonProperty("verificationCode") String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
