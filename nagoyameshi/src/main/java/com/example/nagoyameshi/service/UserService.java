package com.example.nagoyameshi.service;

import java.util.Map;

import jakarta.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Role;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.PasswordEditForm;
import com.example.nagoyameshi.form.SignupForm;
import com.example.nagoyameshi.form.UserEditForm;
import com.example.nagoyameshi.repository.RoleRepository;
import com.example.nagoyameshi.repository.UserRepository;
import com.example.nagoyameshi.repository.VerificationTokenRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final VerificationTokenRepository verificationTokenRepository;

	public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder,
			VerificationTokenRepository verificationTokenRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.verificationTokenRepository = verificationTokenRepository;
	}

	@Transactional
	public User create(SignupForm signupForm) {
		User user = new User();
		Role role = roleRepository.findByName("ROLE_FREE");

		user.setName(signupForm.getName());
		user.setFurigana(signupForm.getFurigana());
		user.setPhoneNumber(signupForm.getPhoneNumber());
		user.setAddress(signupForm.getAddress());
		user.setAge(signupForm.getAge());
		user.setOccupation(signupForm.getOccupation());
		user.setEmail(signupForm.getEmail());
		user.setPassword(passwordEncoder.encode(signupForm.getPassword()));
		user.setRole(role);
		user.setEnabled(false);
		//user.setStripePaymentMethodId(signupForm.getStripePaymentMethodId());

		return userRepository.save(user);
	}

	// メールアドレスが登録済みかどうかをチェックする
	public boolean isEmailRegistered(String email) {
		User user = userRepository.findByEmail(email);
		return user != null;
	}

	// パスワードとパスワード（確認用）の入力値が一致するかどうかをチェックする
	public boolean isSamePassword(String password, String passwordConfirmation) {
		return password.equals(passwordConfirmation);
	}

	// ユーザーを有効にする
	@Transactional
	public void enableUser(User user) {
		user.setEnabled(true);
		userRepository.save(user);
	}

	@Transactional
	public void update(UserEditForm userEditForm) {
		User user = userRepository.getReferenceById(userEditForm.getId());

		user.setEmail(userEditForm.getEmail());
		user.setName(userEditForm.getName());
		user.setFurigana(userEditForm.getFurigana());
		user.setPhoneNumber(userEditForm.getPhoneNumber());
		user.setAddress(userEditForm.getAddress());
		user.setAge(userEditForm.getAge());
		user.setOccupation(userEditForm.getOccupation());

		userRepository.save(user);
	}

	// メールアドレスが変更されたかどうかをチェックする
	public boolean isEmailChanged(UserEditForm userEditForm) {
		User currentUser = userRepository.getReferenceById(userEditForm.getId());
		return !userEditForm.getEmail().equals(currentUser.getEmail());
	}

	// 有料会員にアップグレード
	@Transactional
	public void saveCustomerIdAndUpgrade(Map<String, String> sessionMetadata) {
		System.out.println("UserService:Session Metadata: " + sessionMetadata);

		int userId = Integer.parseInt(sessionMetadata.get("userId"));
		String customerId = sessionMetadata.get("customerId");

		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		user.setStripeCustomerId(customerId);

		Role role = roleRepository.findByName("ROLE_PREMIUM");
		user.setRole(role);

		userRepository.save(user);
	}

	// クレジットカード情報を更新
	public String getCustomerId(Integer userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
		return user.getStripeCustomerId();
	}

	@Transactional
	public void updatePassword(User user, String newPassword) {
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}

	@Transactional
	public void downgrade(int userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		// ROLE_FREEへの変更
		Role freeRole = roleRepository.findByName("ROLE_FREE");
		if (freeRole != null) {
			user.setRole(freeRole);
			userRepository.save(user);
		} else {
			throw new RuntimeException("Role 'ROLE_FREE' not found");
		}
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public void update(Map<String, String> paymentIntentObject) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void refreshAuthenticationByRole(String string) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Transactional
	public void create(@Valid PasswordEditForm passwordEditForm) {
		User user = userRepository.getReferenceById(passwordEditForm.getId());

		user.setPassword(passwordEncoder.encode(passwordEditForm.getPassword()));

		userRepository.save(user);
	}

	public void update(String email) {
		// TODO 自動生成されたメソッド・スタブ		
	}

	@Transactional
	public void updateRole(User user, String roleName) {
		Role role = roleRepository.findByName(roleName);
		user.setRole(role);
		userRepository.save(user);
	}

	@Transactional
	public void deleteUser(Integer userId) {
		// verification_tokensテーブルの関連データを削除
		verificationTokenRepository.deleteByUserId(userId);

		// usersテーブルのデータを削除
		userRepository.deleteById(userId);
	}

}