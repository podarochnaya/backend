package com.vk.itmo.podarochnaya.backend.user.controller;

import com.vk.itmo.podarochnaya.backend.user.dto.UserRequest;
import com.vk.itmo.podarochnaya.backend.user.dto.UserResponse;
import com.vk.itmo.podarochnaya.backend.user.dto.UserUpdateRequest;
import com.vk.itmo.podarochnaya.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getById(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUserById(
            @PathVariable Long userId,
            @RequestBody UserUpdateRequest userRequest) {
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
}
