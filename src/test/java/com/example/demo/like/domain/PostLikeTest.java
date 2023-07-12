package com.example.demo.like.domain;

import com.example.demo.mock.TestClockHolder;
import com.example.demo.post.domain.Post;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PostLikeTest {
    @Test
    void Post와_User_로_PostLike_를_생성할_수_있다() {
        // given
        Post post = Post.builder()
                .id(1L)
                .title("제목")
                .text("내용")
                .createdAt(1630000000000L)
                .updatedAt(null)
                .user(User.builder()
                        .id(2L)
                        .email("user2@gmail.com")
                        .nickname("user2")
                        .verificationCode("abcx")
                        .status(UserStatus.ACTIVATION)
                        .lastLoginAt(1630000000000L)
                        .build())
                .build();

        User user2 = User.builder()
                .id(1L)
                .email("user1@gmail.com")
                .nickname("user1")
                .verificationCode("abcc")
                .status(UserStatus.ACTIVATION)
                .lastLoginAt(1630000000000L)
                .build();

        // when
        Like like = PostLike.from(post, user2, new TestClockHolder(1670000000000L));

        // then
        assertThat(like.getId()).isNull();
        assertThat(like.getCreatedAt()).isEqualTo(1670000000000L);
        assertThat(((PostLike) like).getPost().getId()).isEqualTo(1L);
        assertThat(like.getUser().getId()).isEqualTo(1L);
    }
}
