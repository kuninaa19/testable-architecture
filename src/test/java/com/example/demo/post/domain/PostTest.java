package com.example.demo.post.domain;

import com.example.demo.mock.TestClockHolder;
import com.example.demo.post.controller.request.PostCreateRequest;
import com.example.demo.post.controller.request.PostUpdateRequest;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PostTest {
    @Test
    void PostCreateRequest_객체로_생성할_수_있다() {
        // given
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .title("제목")
                .text("내용")
                .build();

        User writer = User.builder()
                .id(1L)
                .email("user1@gmail.com")
                .nickname("user1")
                .verificationCode("abcx")
                .status(UserStatus.ACTIVATION)
                .lastLoginAt(1630000000000L)
                .build();

        // when
        Post post = Post.from(postCreateRequest, writer, new TestClockHolder(1670000000000L));

        // then
        User expectedUser = User.builder()
                .id(1L)
                .email("user1@gmail.com")
                .nickname("user1")
                .verificationCode("abcx")
                .status(UserStatus.ACTIVATION)
                .lastLoginAt(1630000000000L)
                .build();
        Post expected = Post.builder()
                .id(null)
                .title("제목")
                .text("내용")
                .createdAt(1670000000000L)
                .user(expectedUser)
                .updatedAt(null)
                .likeCount(null)
                .build();

        assertThat(post).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void PostUpdateRequest_객체로_수정할_수_있다() throws IllegalAccessException {
        // given
        PostUpdateRequest postUpdateRequest = PostUpdateRequest.builder()
                .title("제목(수정)")
                .text("내용(수정)")
                .writerId(1L)
                .build();

        User writer = User.builder()
                .id(1L)
                .email("user1@gmail.com")
                .nickname("user1")
                .verificationCode("abcx")
                .status(UserStatus.ACTIVATION)
                .lastLoginAt(1630000000000L)
                .build();

        Post post = Post.builder()
                .id(1L)
                .title("제목")
                .text("내용")
                .createdAt(1670000000000L)
                .updatedAt(null)
                .user(writer)
                .likeCount(null)
                .build();

        // when
        post = post.update(postUpdateRequest, writer, new TestClockHolder(1680000000000L));

        // then
        assertThat(post.getTitle()).isEqualTo("제목(수정)");
        assertThat(post.getText()).isEqualTo("내용(수정)");
        assertThat(post.getUpdatedAt()).isEqualTo(1680000000000L);
    }

    @Test
    void 동일한_작성자가_아닌_경우_에러가_발생한다() {
        // given
        PostUpdateRequest postUpdateRequest = PostUpdateRequest.builder()
                .title("제목(수정)")
                .text("내용(수정)")
                .writerId(1L)
                .build();

        Post post = Post.builder()
                .id(1L)
                .title("제목")
                .text("내용")
                .createdAt(1670000000000L)
                .updatedAt(null)
                .user(User.builder()
                        .id(1L)
                        .email("user1@gmail.com")
                        .nickname("user1")
                        .verificationCode("abcx")
                        .status(UserStatus.ACTIVATION)
                        .lastLoginAt(1630000000000L)
                        .build())
                .likeCount(null)
                .build();

        User updater = User.builder()
                .id(2L)
                .email("user2@gmail.com")
                .nickname("user2")
                .verificationCode("abcx")
                .status(UserStatus.ACTIVATION)
                .lastLoginAt(1630000000000L)
                .build();

        // when
        // then
        assertThatThrownBy(() -> {
            post.update(postUpdateRequest, updater, new TestClockHolder(1680000000000L));
        }).isInstanceOf(IllegalAccessException.class);
    }
}
