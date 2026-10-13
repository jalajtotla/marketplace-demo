package com.example.marketplace.user.service;

import com.example.marketplace.global.constant.SessionKey;
import com.example.marketplace.user.controller.dto.SignInRequestDto;
import com.example.marketplace.user.domain.User;
import com.example.marketplace.user.repository.UserRepository;
import com.example.marketplace.global.util.crypto.CryptoData;
import com.example.marketplace.global.util.crypto.Encryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Service
public class UserSessionLoginService implements LoginService{

    private final UserRepository userRepository;
    private final HttpSession httpSession;
    private final Encryptor encryptor;

    @Override
    public void login(SignInRequestDto dto){
        if (!userRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email does not exist.");
        }
        Optional<User> user = userRepository.findByEmail(dto.getEmail());
        CryptoData cryptoData = CryptoData.WithSaltBuilder()
            .plainText(dto.getPassword())
            .salt(user.get().getSalt())
            .build();
        String encryptedPassword = encryptor.encrypt(cryptoData);

        if(!encryptedPassword.equals(user.get().getPassword())){
            throw new IllegalArgumentException("Incorrect password.");
        }
        httpSession.setAttribute(SessionKey.LOGIN_USER_ID, user.get().getId());
    }

    @Override
    public void logout(){
        httpSession.removeAttribute(SessionKey.LOGIN_USER_ID);
    }

    @Override
    public long getLoginUserId() {
        try {
            return (long)httpSession.getAttribute(SessionKey.LOGIN_USER_ID);
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
    }
    }
