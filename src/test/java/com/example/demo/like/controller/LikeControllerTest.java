package com.example.demo.like.controller;

import com.example.demo.like.controller.port.LikeService;
import com.example.demo.like.controller.request.LikeCreateRequest;
import com.example.demo.like.controller.response.LikeCreateResponse;
import com.example.demo.like.domain.LikeHandlerFactory;
import com.example.demo.like.domain.LikeType;
import com.example.demo.like.domain.PostLike;
import com.example.demo.like.service.LikeHandler;
import com.example.demo.like.service.LikeServiceImpl;
import com.example.demo.like.service.PostLikeHandler;
import com.example.demo.mock.FakeLikeRepository;
import com.example.demo.mock.FakePostRepository;
import com.example.demo.mock.FakeUserRepository;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.post.domain.Post;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LikeControllerTest {

    @Test
    void Post에_대한_Like_를_생성할_수_있다() {
        {
            // given
            FakePostRepository fakePostRepository = new FakePostRepository();
            FakeUserRepository fakeUserRepository = new FakeUserRepository();
            FakeLikeRepository fakeLikeRepository = new FakeLikeRepository();

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

            Post post1 = Post.builder()
                    .id(1L)
                    .title("제목")
                    .text("내용")
                    .createdAt(1665000000000L)
                    .updatedAt(null)
                    .user(user2)
                    .likeCount(0L)
                    .build();
            Post post2 = Post.builder()
                    .id(2L)
                    .title("제목2")
                    .text("내용2")
                    .createdAt(1665000000000L)
                    .updatedAt(null)
                    .user(user2)
                    .likeCount(0L)
                    .build();

            fakePostRepository.save(post1);
            fakePostRepository.save(post2);

            fakeLikeRepository.save(PostLike.builder()
                    .id(1L)
                    .post(post1)
                    .user(user1)
                    .createdAt(1665000000000L)
                    .build());

            List<LikeHandler> likeHandlers = List.of(new PostLikeHandler(fakePostRepository, fakeUserRepository, fakeLikeRepository, new TestClockHolder(1670000000000L)));
            LikeService likeService = new LikeServiceImpl(new LikeHandlerFactory(likeHandlers));

            LikeController likeController = new LikeController(likeService);

            LikeCreateRequest likeCreateRequest = LikeCreateRequest.builder()
                    .userId(1L)
                    .likeType(LikeType.P)
                    .build();

            // when
            ResponseEntity<LikeCreateResponse> result = likeController.like(2L, likeCreateRequest);

            // then
            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(result.getBody().getId()).isEqualTo(2L);
            assertThat(result.getBody().getCreatedAt()).isEqualTo(1670000000000L);
            assertThat(result.getBody().getUser().getId()).isEqualTo(1L);
        }
    }
}
