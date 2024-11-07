package com.vk.itmo.podarochnaya.backend.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRef {
    private Long id;
    private String email;
}
