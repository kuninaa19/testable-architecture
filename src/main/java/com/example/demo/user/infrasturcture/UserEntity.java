package com.example.demo.user.infrasturcture;


import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "USERS")
public class UserEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "last_login_at")
    private Long lastLoginAt;

    public static UserEntity from(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.id = user.getId();
        userEntity.email = user.getEmail();
        userEntity.nickname = user.getNickname();
        userEntity.verificationCode = user.getVerificationCode();
        userEntity.status = user.getStatus();
        userEntity.lastLoginAt = user.getLastLoginAt();

        return userEntity;
    }

    public User toModel() {
        return User.builder()
                .id(id)
                .email(email)
                .nickname(nickname)
                .verificationCode(verificationCode)
                .status(status)
                .lastLoginAt(lastLoginAt)
                .build();
    }
}
