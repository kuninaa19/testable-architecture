package com.example.demo.post.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostUpdateRequest {
    private final long id;
    private final String title;
    private final String text;
    private final long writerId;

    @Builder
    public PostUpdateRequest(@JsonProperty("id") long id, @JsonProperty("title") String title, @JsonProperty("text") String text, @JsonProperty("writerId") long writerId) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.writerId = writerId;
    }
}
