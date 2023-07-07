package com.example.demo.user.controller;

import com.example.demo.mock.*;
import com.example.demo.user.controller.port.UserService;
import com.example.demo.user.controller.request.CreateUserRequest;
import com.example.demo.user.controller.request.UpdateUserRequest;
import com.example.demo.user.controller.request.VerifyUserRequest;
import com.example.demo.user.controller.response.CreateUserResponse;
import com.example.demo.user.controller.response.GetUserResponse;
import com.example.demo.user.controller.response.UpdateUserResponse;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.service.CertificationService;
import com.example.demo.user.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private UserController userController;

    // FIXME controller에 login 메서드도 함께 있다보니 테스트 이름 설정이 매끄럽지않음.
    @Test
    void 상태_값이_ACTIVATION_인_유저는_마지막_로그인_날짜를_변경하고_유저정보를_조회할_수_있다() {
        // given
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        UserService fakeUserService = new UserServiceImpl(fakeUserRepository, new CertificationService(new FakeMailSender()), new TestUuidHolder("abcd-efg-00000"), new TestClockHolder(15500000));
        fakeUserRepository.save(User.builder()
                .id(1L)
                .email("user1@gmail.com")
                .nickname("user1")
                .verificationCode("abcd-efg-98765")
                .status(UserStatus.ACTIVATION)
                .lastLoginAt(14500000L)
                .build());
        fakeUserRepository.save(User.builder()
                .id(2L)
                .email("user2@gmail.com")
                .nickname("user2")
                .verificationCode("abcd-efg-12345")
                .status(UserStatus.UNCERTIFIED)
                .lastLoginAt(14500000L)
                .build());

        userController = new UserController(fakeUserService);

        // when
        ResponseEntity<GetUserResponse> result = userController.getById(1L);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getId()).isEqualTo(1L);
        assertThat(result.getBody().getEmail()).isEqualTo("user1@gmail.com");
        assertThat(result.getBody().getNickname()).isEqualTo("user1");
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVATION);
        assertThat(result.getBody().getLastLoginAt()).isEqualTo(15500000L);
    }

    @Test
    void 유저_상태가_UNCERTIFIED_인_경우_에러가_발생한다() {
        // given
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        UserService fakeUserService = new UserServiceImpl(fakeUserRepository, new CertificationService(new FakeMailSender()), new TestUuidHolder("abcd-efg-00000"), new TestClockHolder(15500000));

        fakeUserRepository.save(User.builder()
                .id(2L)
                .email("user2@gmail.com")
                .nickname("user2")
                .verificationCode("abcd-efg-12345")
                .status(UserStatus.UNCERTIFIED)
                .lastLoginAt(14500000L)
                .build());

        userController = new UserController(fakeUserService);

        // when
        // then
        assertThatThrownBy(() -> {
            userController.getById(2L);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 인증코드가_동일할때_유저_상태를_ACTIVATION_로_변경할_수_있다() {
        // given
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        UserService fakeUserService = new UserServiceImpl(fakeUserRepository, new CertificationService(new FakeMailSender()), new TestUuidHolder("abcd-efg-00000"), new TestClockHolder(15500000));
        fakeUserRepository.save(User.builder()
                .id(2L)
                .email("user2@gmail.com")
                .nickname("user2")
                .verificationCode("abcd-efg-12345")
                .status(UserStatus.UNCERTIFIED)
                .lastLoginAt(14500000L)
                .build());

        userController = new UserController(fakeUserService);

        VerifyUserRequest verifyUserRequest = VerifyUserRequest.builder()
                .verificationCode("abcd-efg-12345")
                .build();

        // when
        ResponseEntity<Void> result = userController.verify(2L, verifyUserRequest);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(fakeUserRepository.findById(2L).get().getStatus()).isEqualTo(UserStatus.ACTIVATION);
    }

    @Test
    void 인증코드가_동일하지않을때_에러가_발생한다() {
        // given
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        UserService fakeUserService = new UserServiceImpl(fakeUserRepository, new CertificationService(new FakeMailSender()), new TestUuidHolder("abcd-efg-00000"), new TestClockHolder(15500000));
        fakeUserRepository.save(User.builder()
                .id(2L)
                .email("user2@gmail.com")
                .nickname("user2")
                .verificationCode("abcd-efg-12345")
                .status(UserStatus.UNCERTIFIED)
                .lastLoginAt(14500000L)
                .build());

        userController = new UserController(fakeUserService);

        VerifyUserRequest verifyUserRequest = VerifyUserRequest.builder()
                .verificationCode("abcd-efg-00000")
                .build();

        // when
        // then
        assertThatThrownBy(() -> {
            userController.verify(2L, verifyUserRequest);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void CreateUserRequest_로_유저를_생성할_수_있다() {
        // given
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        UserService fakeUserService = new UserServiceImpl(fakeUserRepository, new CertificationService(new FakeMailSender()), new TestUuidHolder("abcd-efg-00000"), new TestClockHolder(15500000));
        userController = new UserController(fakeUserService);

        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .email("user1@gmail.com")
                .nickname("user1")
                .build();

        // when
        ResponseEntity<CreateUserResponse> result = userController.create(createUserRequest);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody().getId()).isEqualTo(1L);
        assertThat(result.getBody().getEmail()).isEqualTo("user1@gmail.com");
        assertThat(result.getBody().getNickname()).isEqualTo("user1");
    }

    @Test
    void UpdateUserRequest_로_유저_정보를_수정할_수_있다() {
        // given
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        UserService fakeUserService = new UserServiceImpl(fakeUserRepository, new CertificationService(new FakeMailSender()), new TestUuidHolder("abcd-efg-00000"), new TestClockHolder(15500000));
        fakeUserRepository.save(User.builder()
                .id(2L)
                .email("user2@gmail.com")
                .nickname("user2")
                .verificationCode("abcd-efg-12345")
                .status(UserStatus.UNCERTIFIED)
                .lastLoginAt(14500000L)
                .build());

        userController = new UserController(fakeUserService);

        UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
                .email("user3@gmail.com")
                .nickname("user3")
                .build();

        // when
        ResponseEntity<UpdateUserResponse> result = userController.update(2L, updateUserRequest);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getId()).isEqualTo(2L);
        assertThat(result.getBody().getEmail()).isEqualTo("user3@gmail.com");
        assertThat(result.getBody().getNickname()).isEqualTo("user3");
        assertThat(result.getBody().getVerificationCode()).isEqualTo("abcd-efg-12345");
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.UNCERTIFIED);
        assertThat(result.getBody().getLastLoginAt()).isEqualTo(14500000L);
    }
}
