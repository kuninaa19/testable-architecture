package com.example.demo.post.controller;

import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.mock.FakePostRepository;
import com.example.demo.mock.FakeUserRepository;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.post.controller.port.PostService;
import com.example.demo.post.controller.request.PostCreateRequest;
import com.example.demo.post.controller.request.PostUpdateRequest;
import com.example.demo.post.controller.response.PostCreateResponse;
import com.example.demo.post.controller.response.PostGetResponse;
import com.example.demo.post.controller.response.PostUpdateResponse;
import com.example.demo.post.domain.Post;
import com.example.demo.post.service.PostServiceImpl;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.*;

public class PostControllerTest {

    private PostController postController;

    @Test
    void getById로_Post_를_조회할_수_있다() {
        // given
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        FakePostRepository fakePostRepository = new FakePostRepository();
        PostService fakePostService = new PostServiceImpl(fakeUserRepository, fakePostRepository, new TestClockHolder(1630000000000L));

        User user1 = User.builder()
                .id(1L)
                .email("user1@gmail.com")
                .nickname("user1")
                .verificationCode("abcx")
                .status(UserStatus.UNCERTIFIED)
                .lastLoginAt(0L)
                .build();
        fakeUserRepository.save(user1);

        fakePostRepository.save(Post.builder()
                .id(1L)
                .title("제목")
                .text("내용")
                .createdAt(1665000000000L)
                .updatedAt(null)
                .user(user1)
                .likeCount(0L)
                .build());

        postController = new PostController(fakePostService);

        // when
        ResponseEntity<PostGetResponse> result = postController.getById(1);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getId()).isEqualTo(1L);
        assertThat(result.getBody().getTitle()).isEqualTo("제목");
        assertThat(result.getBody().getText()).isEqualTo("내용");
        assertThat(result.getBody().getCreatedAt()).isEqualTo(1665000000000L);
        assertThat(result.getBody().getUpdatedAt()).isNull();
        assertThat(result.getBody().getWriter().getId()).isEqualTo(1L);
        assertThat(result.getBody().getLikeCount()).isEqualTo(0);
    }

    @Test
    void 작성되지않은_Post_조회할_경우_에러가_발생한다() {
        // given
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        FakePostRepository fakePostRepository = new FakePostRepository();
        PostService fakePostService = new PostServiceImpl(fakeUserRepository, fakePostRepository, new TestClockHolder(1630000000000L));

        postController = new PostController(fakePostService);

        // when
        // then
        assertThatThrownBy(() -> {
            postController.getById(1);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 유저는_Post를_생성할_수_있다() {
        // given
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        FakePostRepository fakePostRepository = new FakePostRepository();
        PostService fakePostService = new PostServiceImpl(fakeUserRepository, fakePostRepository, new TestClockHolder(1630000000000L));
        User user1 = User.builder()
                .id(1L)
                .email("user1@gmail.com")
                .nickname("user1")
                .verificationCode("abcx")
                .status(UserStatus.UNCERTIFIED)
                .lastLoginAt(0L)
                .build();
        fakeUserRepository.save(user1);

        postController = new PostController(fakePostService);

        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .title("제목")
                .text("내용")
                .writerId(1)
                .build();

        // when
        ResponseEntity<PostCreateResponse> result = postController.create(postCreateRequest);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody().getId()).isNotNull();
        assertThat(result.getBody().getTitle()).isEqualTo("제목");
        assertThat(result.getBody().getText()).isEqualTo("내용");
        assertThat(result.getBody().getCreatedAt()).isEqualTo(1630000000000L);
        assertThat(result.getBody().getWriter().getId()).isEqualTo(1);
    }

    @Test
    void 유저는_Post_를_수정할_수_있다() throws IllegalAccessException {
        // given
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        FakePostRepository fakePostRepository = new FakePostRepository();
        PostService fakePostService = new PostServiceImpl(fakeUserRepository, fakePostRepository, new TestClockHolder(1630000000000L));
        User user1 = User.builder()
                .id(1L)
                .email("user1@gmail.com")
                .nickname("user1")
                .verificationCode("abcx")
                .status(UserStatus.UNCERTIFIED)
                .lastLoginAt(0L)
                .build();
        fakeUserRepository.save(user1);

        fakePostRepository.save(Post.builder()
                .id(1L)
                .title("제목")
                .text("내용")
                .createdAt(1625000000000L)
                .updatedAt(null)
                .user(user1)
                .likeCount(null)
                .build());

        postController = new PostController(fakePostService);

        PostUpdateRequest postUpdateRequest = PostUpdateRequest.builder()
                .id(1)
                .title("제목(수정)")
                .text("내용(수정)")
                .writerId(1)
                .build();

        // when
        ResponseEntity<PostUpdateResponse> result = postController.update(postUpdateRequest);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getId()).isEqualTo(1);
        assertThat(result.getBody().getTitle()).isEqualTo("제목(수정)");
        assertThat(result.getBody().getText()).isEqualTo("내용(수정)");
        assertThat(result.getBody().getCreatedAt()).isEqualTo(1625000000000L);
        assertThat(result.getBody().getUpdatedAt()).isEqualTo(1630000000000L);
        assertThat(result.getBody().getWriter().getId()).isEqualTo(1);
    }

    @Test
    void update는_유저와_작성자가_동일하지않을때_u에러를_발생한다() {
        // given
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        FakePostRepository fakePostRepository = new FakePostRepository();
        PostService fakePostService = new PostServiceImpl(fakeUserRepository, fakePostRepository, new TestClockHolder(1630000000000L));
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
                .verificationCode("abcx")
                .status(UserStatus.UNCERTIFIED)
                .lastLoginAt(0L)
                .build();

        fakeUserRepository.save(user1);
        fakeUserRepository.save(user2);
        fakePostRepository.save(Post.builder()
                .id(1L)
                .title("제목")
                .text("내용")
                .createdAt(1625000000000L)
                .updatedAt(null)
                .user(user1)
                .likeCount(null)
                .build());

        postController = new PostController(fakePostService);

        PostUpdateRequest postUpdateRequest = PostUpdateRequest.builder()
                .id(1)
                .title("제목(수정)")
                .text("내용(수정)")
                .writerId(2)
                .build();

        // when
        // then
        assertThatThrownBy(() -> {
            postController.update(postUpdateRequest);
        }).isInstanceOf(IllegalAccessException.class);
    }
}
