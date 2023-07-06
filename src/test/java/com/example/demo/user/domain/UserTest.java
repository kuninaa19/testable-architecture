package com.example.demo.user.domain;

import com.example.demo.mock.TestClockHolder;
import com.example.demo.mock.TestUuidHolder;
import com.example.demo.user.controller.request.CreateUserRequest;
import com.example.demo.user.controller.request.UpdateUserRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class UserTest {

    @Test
    void CreateUserRequest객체로_생성할_수_있다() {
        // given
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .email("user@gmail.com")
                .nickname("user1")
                .build();

        TestUuidHolder uuidHolder = new TestUuidHolder("abcx");

        // when
        User user = User.from(createUserRequest, uuidHolder);

        //then
        User expected = User.builder()
                .id(null)
                .email("user@gmail.com")
                .nickname("user1")
                .verificationCode("abcx")
                .status(UserStatus.UNCERTIFIED)
                .lastLoginAt(null)
                .build();

        assertThat(user).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void UpdateUserRequest객체로_이메일과_닉네임을_수정할_수_있다() {
        // given
        UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
                .email("user2@gmail.com")
                .nickname("user2")
                .build();

        User user = User.builder()
                .id(1L)
                .email("user1@gmail.com")
                .nickname("user1")
                .verificationCode("abcx")
                .status(UserStatus.UNCERTIFIED)
                .lastLoginAt(1000L)
                .build();

        // when
        user = user.update(updateUserRequest);

        // then
        User expected = User.builder()
                .id(1L)
                .email("user2@gmail.com")
                .nickname("user2")
                .verificationCode("abcx")
                .status(UserStatus.UNCERTIFIED)
                .lastLoginAt(1000L)
                .build();

        assertThat(user).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void 동일한_인증코드를_사용한_인증은_활성상태로_변경한다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("user1@gmail.com")
                .nickname("user1")
                .verificationCode("abcx")
                .status(UserStatus.UNCERTIFIED)
                .lastLoginAt(1000L)
                .build();

        // when
        user = user.verify("abcx");

        // then
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVATION);
    }


    @Test
    void 동일하지않은_인증코드로_활성화하려고_하면_에러가_발생한다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("user1@gmail.com")
                .nickname("user1")
                .verificationCode("abcx")
                .status(UserStatus.UNCERTIFIED)
                .lastLoginAt(1000L)
                .build();

        // when
        // then
        assertThatThrownBy(() -> {
            user.verify("abcd");
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 로그인시_마지막_로그인_시간이_변경된다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("user1@gmail.com")
                .nickname("user1")
                .verificationCode("abcx")
                .status(UserStatus.UNCERTIFIED)
                .lastLoginAt(1000L)
                .build();

        // when
        user = user.login(new TestClockHolder(1688656823525L));

        // then
        assertThat(user.getLastLoginAt()).isEqualTo(1688656823525L);

    }
}
