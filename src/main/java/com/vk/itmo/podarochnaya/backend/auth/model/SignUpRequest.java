package com.vk.itmo.podarochnaya.backend.auth.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vk.itmo.podarochnaya.backend.user.dto.UserRequest;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import static java.util.Objects.requireNonNull;

@Data
@Builder
public class SignUpRequest {
    private final @NotNull UserRequest user;

    @JsonCreator
    private SignUpRequest(@Nonnull @JsonProperty("user") @NotNull UserRequest user) {
        this.user = requireNonNull(user, "user");
    }

    @Nonnull
    @JsonProperty("user")
    public @NotNull UserRequest getUser() {
        return user;
    }
}
