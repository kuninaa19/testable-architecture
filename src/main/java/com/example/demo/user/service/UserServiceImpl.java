package com.example.demo.user.service;

import com.example.demo.common.service.port.ClockHolder;
import com.example.demo.common.service.port.UuidHolder;
import com.example.demo.user.controller.port.UserService;
import com.example.demo.user.controller.request.UserCreateRequest;
import com.example.demo.user.controller.request.UserUpdateRequest;
import com.example.demo.user.controller.request.UserVerifyRequest;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CertificationService certificationService;
    private final UuidHolder uuidHolder;
    private final ClockHolder clockHolder;

    public User getById(long id) {
        return userRepository.findByIdAndStatus(id, UserStatus.ACTIVATION).orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public User create(UserCreateRequest userCreateRequest) {
        User user = User.from(userCreateRequest, uuidHolder);
        user = userRepository.save(user);
        certificationService.send(user.getEmail(), "인증코드 발송 : " + user.getId(), user.getVerificationCode());

        return user;
    }

    @Transactional
    public void login(long id) {
        User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        user = user.login(clockHolder);
        userRepository.save(user);
    }

    @Transactional
    public void verify(long id, UserVerifyRequest userVerifyRequest) {
        User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        user = user.verify(userVerifyRequest.getVerificationCode());
        userRepository.save(user);
    }

    @Transactional
    public User update(long id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        user = user.update(userUpdateRequest);
        return userRepository.save(user);
    }
}
