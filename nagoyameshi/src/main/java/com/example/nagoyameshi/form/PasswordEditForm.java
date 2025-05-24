package com.example.nagoyameshi.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class PasswordEditForm {
	@NotNull
    private Integer id;	

	@NotBlank(message = "新しいパスワードを入力してください。")	
	@Length(min = 8, message = "パスワードは8文字以上で入力してください。")
    private String password;
	
	@NotBlank(message = "パスワード（確認用）を入力してください。")
    private String passwordConfirmation;   

	private String token;
   

    // Getters and Setters
    public String getNewPassword() {
        return password;
    }

    public void setNewPassword(String newPassword) {
        this.password = newPassword;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public Integer getId() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	
}