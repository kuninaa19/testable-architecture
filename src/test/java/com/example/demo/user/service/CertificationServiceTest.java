package com.example.demo.user.service;

import com.example.demo.mock.FakeMailSender;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CertificationServiceTest {

    @Test
    void 메일_발송시_파라미터가_잘_주입되어_발송되는지_확인한다() {
        // given
        FakeMailSender fakeMailSender = new FakeMailSender();
        CertificationService certificationService = new CertificationService(fakeMailSender);

        // when
        certificationService.send("test@gmail.com", "인증코드 발송 : " + 1, "abcde");

        // then
        assertThat(fakeMailSender.getTo()).isEqualTo("test@gmail.com");
        assertThat(fakeMailSender.getSubject()).isEqualTo("인증코드 발송 : 1");
        assertThat(fakeMailSender.getText()).isEqualTo("abcde");
    }
}
