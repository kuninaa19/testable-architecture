package com.example.demo.like.service;

import com.example.demo.like.controller.port.LikeService;
import com.example.demo.like.controller.request.LikeCreateRequest;
import com.example.demo.like.domain.Like;
import com.example.demo.like.domain.LikeHandlerFactory;
import com.example.demo.like.domain.LikeType;
import com.example.demo.like.domain.PostLike;
import com.example.demo.mock.FakeLikeRepository;
import com.example.demo.mock.FakePostRepository;
import com.example.demo.mock.FakeUserRepository;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.post.domain.Post;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LikeServiceTest {

    // PostLikeCount는 동시성 문제가 발생할 수 있기 때문에 도메인 객체로 값을 바로 얻을 수 없음
    @Test
    void LikeCreateRequest_로_PostLike_를_생성할_수_있다() {
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

        LikeCreateRequest likeCreateRequest = LikeCreateRequest.builder()
                .userId(1L)
                .likeType(LikeType.P)
                .build();

        // when
        Like like = likeService.like(2L, likeCreateRequest);

        // then
        assertThat(fakeLikeRepository.getCountByPostId(2L)).isEqualTo(1);
        assertThat(like.getId()).isNotNull();
        assertThat(like.getCreatedAt()).isEqualTo(1670000000000L);
        assertThat(like.getUser().getId()).isEqualTo(1L);
        assertThat(((PostLike) like).getPost().getId()).isEqualTo(2L);
    }
}
