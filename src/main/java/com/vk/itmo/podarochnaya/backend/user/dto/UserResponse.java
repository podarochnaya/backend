package com.vk.itmo.podarochnaya.backend.user.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private String fullname;
    private Date birthday;
}
