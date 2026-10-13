package com.example.marketplace.user.controller;

import com.example.marketplace.global.constant.SessionKey;
import com.example.marketplace.global.fixture.UserFixture;
import com.example.marketplace.user.controller.dto.SignInRequestDto;
import com.example.marketplace.user.controller.dto.SignUpRequestDto;
import com.example.marketplace.user.controller.dto.UpdateUserRequestDto;
import com.example.marketplace.global.fixture.UserFixture.*;
import com.example.marketplace.global.template.ControllerTestTemplate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class UserControllerTest extends ControllerTestTemplate {

    @DisplayName("Sign-up succeeds when the email is not a duplicate.")
    @Test
    void signUp() throws Exception {
        // given
        final SignUpRequestDto dto = SignUpRequestDto.builder()
                .name(User1.NAME)
                .email(User1.EMAIL)
                .password(User1.PASSWORD)
                .phone(User1.PHONE)
                .build();

        // when
        final ResultActions actions = mvc.perform(post("/users/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print());

        // then
        actions
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @DisplayName("Sign-up fails when the email is a duplicate.")
    @Test
    void signUpWithDuplicateEmail() throws Exception {
        // given
        final SignUpRequestDto dto1 = SignUpRequestDto.builder()
                .name(User1.NAME)
                .email(User1.EMAIL)
                .password(User1.PASSWORD)
                .phone(User1.PHONE)
                .build();

        final SignUpRequestDto dto2 = SignUpRequestDto.builder()
                .name(User2.NAME)
                .email(User1.EMAIL)
                .password(User2.PASSWORD)
                .phone(User2.PHONE)
                .build();

        // when
        mvc.perform(post("/users/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto1)));

        final ResultActions actions = mvc.perform(post("/users/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto2)))
                .andDo(print());

        // then
        actions
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("Login succeeds when the email exists and the password matches.")
    @Test
    void signIn() throws Exception {
        // given
        final SignUpRequestDto joinDto = SignUpRequestDto.builder()
            .name(User1.NAME)
            .email(User1.EMAIL)
            .password(User1.PASSWORD)
            .phone(User1.PHONE)
            .build();

        final SignInRequestDto loginDto = SignInRequestDto.builder()
            .email(User1.EMAIL)
            .password(User1.PASSWORD)
            .build();

        // when
        mvc.perform(post("/users/sign-up")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(joinDto)));

        final ResultActions actions = mvc.perform(post("/users/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginDto)))
            .andDo(print());

        // then
        actions
            .andExpect(status().isOk())
            .andDo(print());
    }

    @DisplayName("Login fails when the email does not exist.")
    @Test
    void signInWithNotFoundEmail() throws Exception {
        // given
        final SignInRequestDto dto = SignInRequestDto.builder()
            .email(User1.EMAIL)
            .password(User1.PASSWORD)
            .build();

        // when
        final ResultActions actions = mvc.perform(post("/users/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
            .andDo(print());

        // then
        actions
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @DisplayName("Login fails when the email exists but the password does not match.")
    @Test
    void signInWithInvalidPassword() throws Exception {
        // given
        final SignUpRequestDto joinDto = SignUpRequestDto.builder()
            .name(User1.NAME)
            .email(User1.EMAIL)
            .password(User1.PASSWORD)
            .phone(User1.PHONE)
            .build();

        final SignInRequestDto loginDto = SignInRequestDto.builder()
            .email(User1.EMAIL)
            .password(User2.PASSWORD)
            .build();

        // when
        mvc.perform(post("/users/sign-up")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(joinDto)));

        final ResultActions actions = mvc.perform(post("/users/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginDto)))
            .andDo(print());

        // then
        actions
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @DisplayName("Authenticated user can update their profile.")
    @Test
    void updateUserByAuthenticatedUser() throws Exception {
        // given
        final UpdateUserRequestDto dto = UpdateUserRequestDto.builder()
            .name(User2.NAME)
            .password(User2.PASSWORD)
            .phone(User2.PHONE)
            .build();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionKey.LOGIN_USER_ID, UserFixture.User1.ID);

        // when
        final ResultActions actions = mvc.perform(put("/users/my-info")
            .contentType(MediaType.APPLICATION_JSON)
            .session(session)
            .content(objectMapper.writeValueAsString(dto)))
            .andDo(print());

        // then
        actions
            .andExpect(status().isOk())
            .andDo(print());
    }

    @DisplayName("Unauthenticated user cannot update a profile.")
    @Test
    void updateUserByNotAuthenticatedUser() throws Exception {
        // given
        final UpdateUserRequestDto dto = UpdateUserRequestDto.builder()
                .name(User2.NAME)
                .password(User2.PASSWORD)
                .phone(User2.PHONE)
                .build();

        // when
        final ResultActions actions = mvc.perform(put("/users/my-info")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print());

        // then
        actions
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }
}
