package com.example.marketplace.user.service;

import com.example.marketplace.user.controller.dto.SignInRequestDto;

public interface LoginService {

    void login(SignInRequestDto dto);

    void logout();

    long getLoginUserId();
}
