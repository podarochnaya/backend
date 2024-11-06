package com.vk.itmo.podarochnaya.backend.auth.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponse {
    private final @NotBlank String token;
}
