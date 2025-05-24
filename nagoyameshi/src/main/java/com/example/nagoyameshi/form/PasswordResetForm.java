package com.example.nagoyameshi.form;

import jakarta.validation.constraints.NotBlank;

public class PasswordResetForm {
	@NotBlank(message = "メールアドレスを入力してください。")    
    private String email;

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}