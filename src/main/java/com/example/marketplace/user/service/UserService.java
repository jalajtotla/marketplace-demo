package com.example.marketplace.user.service;

import com.example.marketplace.user.controller.dto.SignUpRequestDto;
import com.example.marketplace.user.controller.dto.UpdateUserRequestDto;
import com.example.marketplace.user.domain.EncryptedPassword;
import com.example.marketplace.user.domain.User;
import com.example.marketplace.user.repository.UserRepository;
import com.example.marketplace.global.util.crypto.CryptoData;
import com.example.marketplace.global.util.crypto.Encryptor;
import com.example.marketplace.global.util.crypto.SaltGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    @Qualifier("sha256Encryptor")
    private final Encryptor encryptor;

    public void join(SignUpRequestDto dto){
        if (checkIsUserExist(dto.getEmail())) {
            throw new IllegalArgumentException("Email is already registered.");
        }

        EncryptedPassword pw = encryptPasswordWithSalt(dto.getPassword());
        User user = dto.toEntity(pw.getSalt(), pw.getPassword());
        userRepository.insertUser(user);
    }

    private EncryptedPassword encryptPasswordWithSalt(String plainPassword) {
        String salt = SaltGenerator.generateSalt();
        CryptoData cryptoData = CryptoData.WithSaltBuilder()
                .plainText(plainPassword)
                .salt(salt)
                .build();
        String encryptedPassword = encryptor.encrypt(cryptoData);
        return EncryptedPassword.builder()
                .salt(salt)
                .password(encryptedPassword)
                .build();
    }

    private boolean checkIsUserExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public void updateUser(long id, UpdateUserRequestDto dto){
        EncryptedPassword pw = encryptPasswordWithSalt(dto.getPassword());
        User user = dto.toEntity(id, pw.getSalt(), pw.getPassword());
        userRepository.updateUser(user);
    }
}


