package com.vk.itmo.podarochnaya.backend.auth.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import static java.util.Objects.requireNonNull;

@Data
@Builder
public class SignUpRequest {
    private final @NotNull UserDTO user;

    @JsonCreator
    private SignUpRequest(@Nonnull @JsonProperty("user") @NotNull UserDTO user) {
        this.user = requireNonNull(user, "user");
    }

    @Nonnull
    @JsonProperty("user")
    public @NotNull UserDTO getUser() {
        return user;
    }
}
