package com.example.demo.post.controller.response;

import com.example.demo.post.domain.Post;
import com.example.demo.user.controller.response.GetUserResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostCreateResponse {
    private Long id;

    private String title;

    private String text;

    private Long createdAt;

    private GetUserResponse writer;

    public static PostCreateResponse from(Post post) {
        return PostCreateResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .text(post.getText())
                .createdAt(post.getCreatedAt())
                .writer(GetUserResponse.from(post.getUser()))
                .build();
    }
}
