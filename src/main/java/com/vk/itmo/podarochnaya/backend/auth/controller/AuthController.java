package com.vk.itmo.podarochnaya.backend.auth.controller;


import com.vk.itmo.podarochnaya.backend.auth.model.JwtResponse;
import com.vk.itmo.podarochnaya.backend.auth.model.SignInRequest;
import com.vk.itmo.podarochnaya.backend.auth.model.SignUpRequest;
import com.vk.itmo.podarochnaya.backend.auth.model.UserDTO;
import com.vk.itmo.podarochnaya.backend.auth.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public JwtResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return JwtResponse.builder()
                .token(authenticationService.signUp(request))
                .build();
    }

    @PostMapping("/sign-in")
    public JwtResponse signIn(@RequestBody @Valid SignInRequest request) {
        return JwtResponse.builder()
                .token(authenticationService.signIn(request))
                .build();
    }
}
