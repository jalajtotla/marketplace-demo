package com.example.marketplace.user.controller.dto;

import com.example.marketplace.user.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class SignUpRequestDto {

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Email is required.")
    @Email(message = "Email format is invalid.")
    private String email;

    @NotBlank(message = "Password is required.")
    private String password;

    @NotBlank(message = "Phone number is required.")
    @Pattern(regexp="[0-9]{10,11}", message = "Phone number must be 10-11 digits.")
    private String phone;

    @Builder
    public SignUpRequestDto(String name, String email, String password, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public User toEntity(String salt, String encryptedPassword){
        return User.builder()
                .name(name)
                .email(email)
                .salt(salt)
                .password(encryptedPassword)
                .phone(phone)
                .build();
    }
}
