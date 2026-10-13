package com.example.marketplace.user.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.marketplace.user.controller.dto.SignInRequestDto;
import com.example.marketplace.user.domain.User;
import com.example.marketplace.global.fixture.UserFixture.*;
import com.example.marketplace.user.repository.UserRepository;
import com.example.marketplace.global.util.crypto.Encryptor;

@ExtendWith(MockitoExtension.class)
public class UserSessionLoginServiceTest {

	@InjectMocks
	private UserSessionLoginService userSessionLoginService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private Encryptor encryptor;

	@Mock
	private HttpSession httpSession;

	@DisplayName("Login succeeds when the email exists and the password matches.")
	@Test
	void signIn() {
		// given
		final SignInRequestDto dto = SignInRequestDto.builder()
			.email(User1.EMAIL)
			.password(User1.PASSWORD)
			.build();
		final Optional<User> user = Optional.of(User1.USER);
		final String encryptedPassword = User1.ENCRYPTED_PASSWORD;

		given(userRepository.findByEmail(dto.getEmail())).willReturn(user);
		given(encryptor.encrypt(any())).willReturn(encryptedPassword);

		// when
		userSessionLoginService.login(dto);

		// then
		then(httpSession).should(times(1)).setAttribute(any(), any());
	}

	@DisplayName("Throws an error when the email does not exist.")
	@Test
	void signInWithNotFoundEmail() {
		//given
		final SignInRequestDto dto = SignInRequestDto.builder()
			.email(User1.EMAIL)
			.password(User1.PASSWORD)
			.build();
		final Optional<User> notFoundUser = Optional.ofNullable(null);

		given(userRepository.findByEmail(any())).willReturn(notFoundUser);

		// then
		assertThrows(IllegalArgumentException.class, () -> userSessionLoginService.login(dto));
	}

	@DisplayName("Throws an error when the password does not match.")
	@Test
	void signInWithInvalidPassword() {
		// given
		final SignInRequestDto dto = SignInRequestDto.builder()
			.email(User1.EMAIL)
			.password(User2.PASSWORD)
			.build();
		final Optional<User> user = Optional.of(User1.USER);
		final String encryptedPassword = User2.ENCRYPTED_PASSWORD;

		given(userRepository.findByEmail(any())).willReturn(user);
		given(encryptor.encrypt(any())).willReturn(encryptedPassword);

		// then
		assertThrows(IllegalArgumentException.class, () -> userSessionLoginService.login(dto));
	}
}
