package com.example.nagoyameshi.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.entity.VerificationToken;
import com.example.nagoyameshi.event.PasswordResetEvent;
import com.example.nagoyameshi.repository.UserRepository;

@Service
public class PasswordResetService {
    private final UserService userService;
    private final VerificationTokenService verificationTokenService;
    private final ApplicationEventPublisher eventPublisher;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public PasswordResetService(UserService userService, VerificationTokenService verificationTokenService, 
    		ApplicationEventPublisher eventPublisher, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userService = userService;
        this.verificationTokenService = verificationTokenService;
        this.eventPublisher = eventPublisher;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void sendResetPasswordEmail(String email, String requestUrl) {
        User user = userService.findByEmail(email);
        PasswordResetEvent passwordResetEvent = new PasswordResetEvent(this, user, requestUrl);
        eventPublisher.publishEvent(passwordResetEvent);
    }

    public boolean isEmailRegistered(String email) {
        User user = userService.findByEmail(email);
        return user != null;
    }
    
    // パスワードとパスワード（確認用）の入力値が一致するかどうかをチェックする
    public boolean isSamePassword(String password, String passwordConfirmation) {
        return password.equals(passwordConfirmation);
    }    

    public VerificationToken validatePasswordResetToken(String token) {
        VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);
        if (verificationToken == null || verificationToken.isExpired()) {
            throw new IllegalArgumentException("無効なトークンです。");
        }
        return verificationToken;
    }

    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword)); // 新しいパスワードのエンコード
        userRepository.save(user); // データベースへの保存
    }
}
    