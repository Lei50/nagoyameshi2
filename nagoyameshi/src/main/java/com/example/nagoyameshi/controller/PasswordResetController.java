package com.example.nagoyameshi.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.entity.VerificationToken;
import com.example.nagoyameshi.form.PasswordEditForm;
import com.example.nagoyameshi.form.PasswordResetForm;
import com.example.nagoyameshi.service.PasswordResetService;
import com.example.nagoyameshi.service.VerificationTokenService;

@Controller
@RequestMapping("/password")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;
    private final VerificationTokenService verificationTokenService; 
   
    public PasswordResetController(PasswordResetService passwordResetService, VerificationTokenService verificationTokenService) {
        this.passwordResetService = passwordResetService;     
        this.verificationTokenService = verificationTokenService;
    }

    @GetMapping("/reset")
    public String showPasswordResetForm(Model model) {
        model.addAttribute("passwordResetForm", new PasswordResetForm());
        return "auth/passwordReset";
    }

    @PostMapping("/reset")
    public String handleResetPassword(@ModelAttribute @Valid PasswordResetForm passwordResetForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
        if (!passwordResetService.isEmailRegistered(passwordResetForm.getEmail())) {
            FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "登録されていないメールアドレスです。");
            bindingResult.addError(fieldError);
        }
        if (bindingResult.hasErrors()) {
            return "auth/passwordReset";
        }
        try {
            String requestUrl = httpServletRequest.getRequestURL().toString();
            passwordResetService.sendResetPasswordEmail(passwordResetForm.getEmail(), requestUrl);
            redirectAttributes.addFlashAttribute("successMessage", "ご入力いただいたメールアドレスに認証メールを送信しました。メールに記載されているリンクをクリックし、パスワード再発行を完了してください。");
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("email", "error.passwordResetForm", e.getMessage());
            return "auth/passwordReset";
        }
        return "redirect:/";
    }

    @GetMapping("/reset/confirm")
    public String showResetPasswordConfirmForm(@RequestParam("token") String token, Model model) {
        try {
            passwordResetService.validatePasswordResetToken(token);
            model.addAttribute("token", token);
            model.addAttribute("passwordEditForm", new PasswordEditForm()); 
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/resetPasswordConfirm";
        }
        return "auth/passwordEdit";
    }

    @PostMapping("/reset/confirm")
    public String handleResetPasswordConfirm(@ModelAttribute @Valid PasswordEditForm passwordEditForm, 
    		BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    	
    	String token = passwordEditForm.getToken();    	
    	VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);
    	
    	 // パスワードとパスワード（確認用）の入力値が一致しなければ、BindingResultオブジェクトにエラー内容を追加する
        if (!passwordResetService.isSamePassword(passwordEditForm.getPassword(), passwordEditForm.getPasswordConfirmation())) {
            FieldError fieldError = new FieldError(bindingResult.getObjectName(), "password", "パスワードが一致しません。");
            bindingResult.addError(fieldError);
        }      
        
        User user = verificationToken.getUser();
        
        passwordResetService.updatePassword(user, passwordEditForm.getPassword());
        redirectAttributes.addFlashAttribute("successMessage", "パスワードを更新しました。");
        
        return "redirect:/login";
    }
}