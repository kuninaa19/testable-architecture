package com.example.demo.post.controller.response;

import com.example.demo.post.domain.Post;
import com.example.demo.user.controller.response.UserGetResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostGetResponse {
    private Long id;
    private String title;
    private String text;
    private Long createdAt;
    private Long updatedAt;
    private UserGetResponse writer;
    private Long likeCount;

    public static PostGetResponse from(Post post) {
        return PostGetResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .text(post.getText())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .writer(UserGetResponse.from(post.getUser()))
                .likeCount(post.getLikeCount())
                .build();
    }
}
