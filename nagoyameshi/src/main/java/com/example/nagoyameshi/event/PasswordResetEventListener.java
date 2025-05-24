package com.example.nagoyameshi.event;

import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.entity.VerificationToken;
import com.example.nagoyameshi.repository.VerificationTokenRepository;
import com.example.nagoyameshi.service.VerificationTokenService;

@Component
public class PasswordResetEventListener {
    private final VerificationTokenService verificationTokenService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final JavaMailSender javaMailSender;

    public PasswordResetEventListener(VerificationTokenService verificationTokenService, VerificationTokenRepository verificationTokenRepository, JavaMailSender javaMailSender) {
        this.verificationTokenService = verificationTokenService;
        this.verificationTokenRepository = verificationTokenRepository;
        this.javaMailSender = javaMailSender;
    }

    @EventListener
    public void handlePasswordResetEvent(PasswordResetEvent passwordResetEvent) {
        User user = passwordResetEvent.getUser();
        String token = UUID.randomUUID().toString();

        // 既存のトークンを削除
        VerificationToken existingToken = verificationTokenRepository.findByUserId(user.getId());
        if (existingToken != null) {
            verificationTokenRepository.delete(existingToken);
        }

        // 新しいトークンを生成
        verificationTokenService.create(user, token);

        String recipientAddress = user.getEmail();
        String subject = "パスワードリセットのリクエスト";
        String resetUrl = passwordResetEvent.getResetUrl() + "/confirm?token=" + token;
        String message = "以下のリンクをクリックしてパスワードをリセットしてください。";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(recipientAddress);
        mailMessage.setSubject(subject);
        mailMessage.setText(message + "\n" + resetUrl);

        javaMailSender.send(mailMessage);
    }
}