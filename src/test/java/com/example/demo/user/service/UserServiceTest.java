package com.example.demo.user.service;

import com.example.demo.mock.FakeMailSender;
import com.example.demo.mock.FakeUserRepository;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.mock.TestUuidHolder;
import com.example.demo.user.controller.port.UserService;
import com.example.demo.user.controller.request.CreateUserRequest;
import com.example.demo.user.controller.request.UpdateUserRequest;
import com.example.demo.user.controller.request.VerifyUserRequest;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        FakeMailSender fakeMailSender = new FakeMailSender();
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        TestUuidHolder uuidHolder = new TestUuidHolder("abcx");
        TestClockHolder clockHolder = new TestClockHolder(15500000);

        userService = new UserServiceImpl(fakeUserRepository, new CertificationService(fakeMailSender), uuidHolder, clockHolder);

        fakeUserRepository.save(User.builder()
                .id(1L)
                .email("user1@gmail.com")
                .nickname("user1")
                .verificationCode("abcx")
                .status(UserStatus.UNCERTIFIED)
                .lastLoginAt(0L)
                .build());

        fakeUserRepository.save(User.builder()
                .id(2L)
                .email("user2@gmail.com")
                .nickname("user2")
                .verificationCode("abcxy")
                .status(UserStatus.ACTIVATION)
                .lastLoginAt(0L)
                .build());
    }

    @Test
    void 유저_상태가_ACTIVATION_인_경우_id_값으로_유저를_찾을_수_있다() {
        // given
        // when
        User user = userService.getById(2L);

        // then
        assertThat(user.getId()).isEqualTo(2L);
        assertThat(user.getEmail()).isEqualTo("user2@gmail.com");
        assertThat(user.getNickname()).isEqualTo("user2");
        assertThat(user.getVerificationCode()).isEqualTo("abcxy");
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVATION);
        assertThat(user.getLastLoginAt()).isEqualTo(0L);
    }


    @Test
    void 유저_상태가_UNCERTIFIED_인_경우_에러가_발생한다() {
        // given
        // when
        // then
        assertThatThrownBy(() ->
                userService.getById(1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void CreateUserRequest_로_유저를_생성할_수_있다() {
        // given
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .email("user3@gmail.com")
                .nickname("user3").build();

        // when
        User user = userService.create(createUserRequest);

        // then
        assertThat(user.getId()).isEqualTo(3L);
        assertThat(user.getEmail()).isEqualTo("user3@gmail.com");
        assertThat(user.getNickname()).isEqualTo("user3");
        assertThat(user.getStatus()).isEqualTo(UserStatus.UNCERTIFIED);
        assertThat(user.getLastLoginAt()).isNull();
    }

    @Test
    void login_을_하면_마지막_로그인_시간이_변경된다() {
        // given
        // when
        userService.login(2L);

        // then
        User user = userService.getById(2L);
        assertThat(user.getLastLoginAt()).isEqualTo(15500000L);
    }

    @Test
    void 인증코드가_동일할때_유저_상태를_ACTIVATION_로_변경할_수_있다() {
        // given
        VerifyUserRequest verifyUserRequest = VerifyUserRequest.builder()
                .verificationCode("abcx")
                .build();

        // when
        userService.verify(1L, verifyUserRequest);

        // then
        User user = userService.getById(1L);
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVATION);
    }

    @Test
    void 인증코드가_동일하지않을때_에러가_발생한다() {
        // given
        VerifyUserRequest verifyUserRequest = VerifyUserRequest.builder()
                .verificationCode("abcv")
                .build();

        // when
        // then
        assertThatThrownBy(() -> {
            userService.verify(1L, verifyUserRequest);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void UpdateUserRequest_로_유저를_수정할_수_있다() {
        // given
        UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
                .email("update_user@gmail.com")
                .nickname("updateUser")
                .build();

        // when
        User user = userService.update(1L, updateUserRequest);

        // then
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("update_user@gmail.com");
        assertThat(user.getNickname()).isEqualTo("updateUser");
    }
}
