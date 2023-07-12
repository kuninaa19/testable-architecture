package com.example.demo.post.service;

import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.mock.FakePostRepository;
import com.example.demo.mock.FakeUserRepository;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.post.controller.port.PostService;
import com.example.demo.post.controller.request.PostCreateRequest;
import com.example.demo.post.controller.request.PostUpdateRequest;
import com.example.demo.post.domain.Post;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class PostServiceTest {

    private PostService postService;

    @BeforeEach
    void setUp() {
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        FakePostRepository fakePostRepository = new FakePostRepository();
        TestClockHolder testClockHolder = new TestClockHolder(1670000000000L);

        postService = new PostServiceImpl(fakeUserRepository, fakePostRepository, testClockHolder);

        User user1 = User.builder()
                .id(1L)
                .email("user1@gmail.com")
                .nickname("user1")
                .verificationCode("abcx")
                .status(UserStatus.UNCERTIFIED)
                .lastLoginAt(0L)
                .build();
        User user2 = User.builder()
                .id(2L)
                .email("user2@gmail.com")
                .nickname("user2")
                .verificationCode("abcxy")
                .status(UserStatus.ACTIVATION)
                .lastLoginAt(0L)
                .build();

        fakeUserRepository.save(user1);
        fakeUserRepository.save(user2);

        fakePostRepository.save(Post.builder()
                .id(1L)
                .title("제목")
                .text("내용")
                .createdAt(1665000000000L)
                .updatedAt(null)
                .user(user2)
                .likeCount(0L)
                .build());
    }

    @Test
    void getById_로_객체를_조회할_수_있다() {
        // given
        // when
        Post post = postService.getById(1);

        // then
        Post expected = Post.builder()
                .id(1L)
                .title("제목")
                .text("내용")
                .createdAt(1665000000000L)
                .user(User.builder()
                        .id(2L)
                        .email("user2@gmail.com")
                        .nickname("user2")
                        .verificationCode("abcxy")
                        .status(UserStatus.ACTIVATION)
                        .lastLoginAt(0L)
                        .build())
                .updatedAt(null)
                .likeCount(0L)
                .build();

        assertThat(post).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void getById_는_객체가_없다면_에러를_발생한다() {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            postService.getById(2);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void PostCreateRequest_로_객체를_생성할_수_있다() {
        // given
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .title("제목2")
                .text("내용2")
                .writerId(1)
                .build();

        // when
        Post post = postService.create(postCreateRequest);

        // then
        assertThat(post.getId()).isNotNull();
        assertThat(post.getTitle()).isEqualTo("제목2");
        assertThat(post.getText()).isEqualTo("내용2");
        assertThat(post.getCreatedAt()).isEqualTo(1670000000000L);
    }

    @Test
    void PostUpdateRequest_로_객체를_수정할_수_있다() throws IllegalAccessException {
        // given
        PostUpdateRequest postUpdateRequest = PostUpdateRequest.builder()
                .id(1)
                .title("제목(수정)")
                .text("내용(수정)")
                .writerId(2)
                .build();

        // when
        Post post = postService.update(postUpdateRequest);

        // then
        assertThat(post.getTitle()).isEqualTo("제목(수정)");
        assertThat(post.getText()).isEqualTo("내용(수정)");
        assertThat(post.getUpdatedAt()).isEqualTo(1670000000000L);
    }

    @Test
    void update는_유저와_작성자가_동일하지않다면_에러를_발생한다() throws IllegalAccessException {
        // given
        PostUpdateRequest postUpdateRequest = PostUpdateRequest.builder()
                .id(1)
                .title("제목(수정)")
                .text("내용(수정)")
                .writerId(1)
                .build();

        // when
        assertThatThrownBy(() -> {
            postService.update(postUpdateRequest);
        }).isInstanceOf(IllegalAccessException.class);

    }
}
