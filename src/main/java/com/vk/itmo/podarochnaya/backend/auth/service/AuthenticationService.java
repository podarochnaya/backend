package com.vk.itmo.podarochnaya.backend.auth.service;

import com.vk.itmo.podarochnaya.backend.auth.model.SignInRequest;
import com.vk.itmo.podarochnaya.backend.auth.model.SignUpRequest;
import com.vk.itmo.podarochnaya.backend.jwt.JwtService;
import com.vk.itmo.podarochnaya.backend.user.jpa.UserEntity;
import com.vk.itmo.podarochnaya.backend.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public String signUp(SignUpRequest request) {
        var user = UserEntity.builder()
            .birthday(request.getUser().getBirthday())
            .email(request.getUser().getEmail())
            .passwordHash(passwordEncoder.encode(request.getUser().getPassword()))
            .fullname(request.getUser().getFullname())
            .build();

        userService.create(user);
        return jwtService.generateToken(user);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public String signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));
        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getEmail());
        return jwtService.generateToken(user);
    }
}
