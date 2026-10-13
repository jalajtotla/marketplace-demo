package com.example.marketplace.user.controller;

import com.example.marketplace.auth.AuthRequired;
import com.example.marketplace.global.common.StatusEnum;
import com.example.marketplace.global.common.SuccessResponse;
import com.example.marketplace.user.controller.dto.*;
import com.example.marketplace.user.service.LoginService;
import com.example.marketplace.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;



@RequestMapping("/users")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final LoginService loginService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public SuccessResponse signUp(@Valid @RequestBody final SignUpRequestDto requestDto) {
        userService.join(requestDto);
        SuccessResponse res = SuccessResponse.builder()
                .status(StatusEnum.CREATED)
                .message("Sign-up successful")
                .build();
        return res;
    }

    @PostMapping("/login")
    public SuccessResponse loginUser(@Valid @RequestBody final SignInRequestDto requestDto) {
        loginService.login(requestDto);
        SuccessResponse res = SuccessResponse.builder()
                .status(StatusEnum.OK)
                .message("Login successful")
                .build();
        return res;
    }

    @GetMapping("/logout")
    public void logoutUser(){
        loginService.logout();
    }

    @AuthRequired
    @PutMapping("/my-info")
    public void updateUser(@Valid @RequestBody final UpdateUserRequestDto requestDto){
        long id = loginService.getLoginUserId();
        userService.updateUser(id, requestDto);
    }
}
