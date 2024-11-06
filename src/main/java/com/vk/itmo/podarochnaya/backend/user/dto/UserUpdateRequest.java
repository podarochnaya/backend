package com.vk.itmo.podarochnaya.backend.user.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserUpdateRequest {
    private String email;
    private String fullName;
    private String password;
    private Date birthday;
}
