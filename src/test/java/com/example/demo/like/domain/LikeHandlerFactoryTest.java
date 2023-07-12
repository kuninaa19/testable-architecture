package com.example.demo.like.domain;

import com.example.demo.like.controller.request.LikeCreateRequest;
import com.example.demo.like.service.LikeHandler;
import com.example.demo.like.service.PostLikeHandler;
import com.example.demo.mock.FakeLikeRepository;
import com.example.demo.mock.FakePostRepository;
import com.example.demo.mock.FakeUserRepository;
import com.example.demo.mock.TestClockHolder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LikeHandlerFactoryTest {
    @Test
    void LikeCreateRequest_의_LikeType_에_따라_다른_LikeHandler_인스턴스를_리턴할_수_있다() {
        // given
        List<LikeHandler> likeHandlers = List.of(new PostLikeHandler(new FakePostRepository(), new FakeUserRepository(), new FakeLikeRepository(), new TestClockHolder(1670000000000L)));
        LikeHandlerFactory likeHandlerFactory = new LikeHandlerFactory(likeHandlers);

        LikeCreateRequest likeCreateRequest = LikeCreateRequest.builder()
                .likeType(LikeType.P)
                .build();

        // when
        LikeHandler likeHandler = likeHandlerFactory.createLikeHandler(likeCreateRequest);

        // then
        assertThat(likeHandler).isInstanceOf(PostLikeHandler.class);
    }

    @Test
    void LikeCreateRequest_의_LikeType_에_포함되지않는_enum_값은_에러가_발생한다() {
        // given
        List<LikeHandler> likeHandlers = List.of(new PostLikeHandler(new FakePostRepository(), new FakeUserRepository(), new FakeLikeRepository(), new TestClockHolder(1670000000000L)));
        LikeHandlerFactory likeHandlerFactory = new LikeHandlerFactory(likeHandlers);

        LikeCreateRequest likeCreateRequest = LikeCreateRequest.builder()
                .likeType(null)
                .build();

        // when
        // then
        assertThatThrownBy(() -> {
            likeHandlerFactory.createLikeHandler(likeCreateRequest);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
