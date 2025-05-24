package com.example.nagoyameshi.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CardForm {
    @NotBlank(message = "カード番号を入力してください。")
    @Pattern(regexp = "\\d{16}", message = "カード番号は16桁の数字で入力してください。")
    private String cardNumber;

    @NotBlank(message = "有効期限を入力してください。")
    @Pattern(regexp = "(0[1-9]|1[0-2])/\\d{2}", message = "有効期限はMM/YY形式で入力してください。")
    private String expiryDate;

    @NotBlank(message = "セキュリティコードを入力してください。")
    @Pattern(regexp = "\\d{3,4}", message = "セキュリティコードは3桁または4桁の数字で入力してください。")
    private String cvc;

	public Object getCardNumber() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public String getExpiryDate() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public Object getCvc() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
