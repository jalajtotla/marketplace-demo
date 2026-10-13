package com.example.marketplace.user.controller.dto;

import com.example.marketplace.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class UpdateUserRequestDto {

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Password is required.")
    private String password;

    @NotBlank(message = "Phone number is required.")
    @Pattern(regexp="[0-9]{10,11}", message = "Phone number must be 10-11 digits.")
    private String phone;

    @Builder
    public UpdateUserRequestDto(String name, String password, String phone) {
        this.name = name;
        this.password = password;
        this.phone = phone;
    }

    public User toEntity(long id, String salt, String encryptedPassword){
        return User.builder()
                .id(id)
                .name(name)
                .salt(salt)
                .password(encryptedPassword)
                .phone(phone)
                .build();
    }
}
