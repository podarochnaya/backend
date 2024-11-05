package com.vk.itmo.podarochnaya.backend.auth.controller;


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
    public String signUp(@RequestBody @Valid UserDTO request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/sign-in")
    public String signIn(@RequestBody @Valid UserDTO request) {
        return authenticationService.signIn(request);
    }
}
