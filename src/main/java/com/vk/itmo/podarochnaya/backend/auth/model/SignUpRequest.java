package com.vk.itmo.podarochnaya.backend.auth.model;

import com.vk.itmo.podarochnaya.backend.user.dto.UserCreateRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpRequest {
    private @NotNull UserCreateRequest user;
}
