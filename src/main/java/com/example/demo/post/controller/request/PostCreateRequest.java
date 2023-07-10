package com.example.demo.post.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostCreateRequest {
    private final long writerId;
    private final String title;
    private final String text;

    @Builder
    public PostCreateRequest(@JsonProperty("writerId") long writerId, @JsonProperty("title") String title, @JsonProperty("text") String text) {
        this.writerId = writerId;
        this.title = title;
        this.text = text;
    }
}
