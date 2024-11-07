package com.vk.itmo.podarochnaya.backend.auth.model;

import com.vk.itmo.podarochnaya.backend.user.dto.UserCreateRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private @NotNull @Valid UserCreateRequest user;
}
