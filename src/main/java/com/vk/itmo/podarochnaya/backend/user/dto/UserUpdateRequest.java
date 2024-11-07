package com.vk.itmo.podarochnaya.backend.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class UserUpdateRequest {
    @Email(message = "Email must be a valid email address")
    private String email;

    @Size(max = 255, message = "Full name must be at most 255 characters")
    private String fullName;

    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    private String password;

    @Past(message = "Birthday must be in the past")
    private Date birthday;
}
