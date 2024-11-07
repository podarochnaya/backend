package com.vk.itmo.podarochnaya.backend.user.controller;

import com.vk.itmo.podarochnaya.backend.user.dto.UserResponse;
import com.vk.itmo.podarochnaya.backend.user.dto.UserUpdateRequest;
import com.vk.itmo.podarochnaya.backend.user.mapper.UserMapper;
import com.vk.itmo.podarochnaya.backend.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    private final UserMapper mapper;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(mapper.toUserResponse(userService.getById(userId)));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUserById(
        @PathVariable Long userId,
        @Valid @RequestBody UserUpdateRequest userRequest) {
        return ResponseEntity.ok(userService.updateUserById(userId, userRequest));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Long> deleteUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.deleteById(userId));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> listAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        return ResponseEntity.ok(userService.getMe());
    }
}
