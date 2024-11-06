package com.vk.itmo.podarochnaya.backend.auth.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import static java.util.Objects.requireNonNull;

@Data
@Builder
public class SignInRequest {
    private final @NotBlank String email;
    private final @NotBlank String password;

    @JsonCreator
    private SignInRequest(@Nonnull @JsonProperty("email") @NotBlank String email,
                          @Nonnull @JsonProperty("password") @NotBlank String password) {
        this.email = requireNonNull(email, "email");
        this.password = requireNonNull(password, "password");
    }

    @Nonnull
    @JsonProperty("email")
    public @NotBlank String getEmail() {
        return email;
    }

    @Nonnull
    @JsonProperty("password")
    public @NotBlank String getPassword() {
        return password;
    }
}
