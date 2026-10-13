package com.example.marketplace.user.service;

import com.example.marketplace.user.controller.dto.SignUpRequestDto;
import com.example.marketplace.user.controller.dto.UpdateUserRequestDto;
import com.example.marketplace.user.domain.User;
import com.example.marketplace.user.repository.UserRepository;
import com.example.marketplace.global.util.crypto.Encryptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.marketplace.global.fixture.UserFixture.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Encryptor encryptor;

    @DisplayName("Sign-up succeeds when the email is not a duplicate.")
    @Test
    void signUp() {
        // given
        final SignUpRequestDto dto = SignUpRequestDto.builder()
                .name(User1.NAME)
                .email(User1.EMAIL)
                .password(User1.PASSWORD)
                .phone(User1.PHONE)
                .build();
        final Optional<User> notFoundUser = Optional.ofNullable(null);

        given(userRepository.findByEmail(any())).willReturn(notFoundUser);

        // when
        userService.join(dto);

        // then
        then(userRepository).should(times(1)).insertUser(any());

    }

    @DisplayName("Throws an error when the email is a duplicate.")
    @Test
    void signUpWithDuplicateEmail() {
        // given
        final SignUpRequestDto dto = SignUpRequestDto.builder()
            .name(User1.NAME)
            .email(User1.EMAIL)
            .password(User1.PASSWORD)
            .phone(User1.PHONE)
            .build();
        final Optional<User> user = Optional.of(User1.USER);

        given(userRepository.findByEmail(any())).willReturn(user);

        // then
        assertThrows(IllegalArgumentException.class, () -> userService.join(dto));
    }

    @DisplayName("Updates the user's information.")
    @Test
    void updateUser() {
        // given
        final UpdateUserRequestDto dto = UpdateUserRequestDto.builder()
                .name(User2.NAME)
                .password(User2.PASSWORD)
                .phone(User2.PHONE)
                .build();
        final long id = User1.ID;

        // when
        userService.updateUser(id, dto);

        // then
        then(userRepository).should(times(1)).updateUser(any());
    }
}