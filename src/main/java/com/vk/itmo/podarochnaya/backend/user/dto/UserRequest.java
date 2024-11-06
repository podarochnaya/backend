package com.vk.itmo.podarochnaya.backend.user.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.Data;

import static java.util.Objects.requireNonNull;

@Data
public class UserRequest {
    private final @NotBlank String email;

    private final @NotBlank String fullName;

    private final @NotBlank String password;

    private final @NotNull Date birthday;

    @JsonCreator
    private UserRequest(@Nonnull @JsonProperty("email") @NotBlank String email,
                        @Nonnull @JsonProperty("fullName") @NotBlank String fullName,
                        @Nonnull @JsonProperty("password") @NotBlank String password,
                        @Nonnull @JsonProperty("birthday") @NotNull Date birthday) {
        this.email = requireNonNull(email, "email");
        this.fullName = requireNonNull(fullName, "fullName");
        this.password = requireNonNull(password, "password");
        this.birthday = requireNonNull(birthday, "birthday");
    }

    @Nonnull
    @JsonProperty("email")
    public @NotBlank String getEmail() {
        return email;
    }

    @Nonnull
    @JsonProperty("fullName")
    public @NotBlank String getFullname() {
        return fullName;
    }

    @Nonnull
    @JsonProperty("password")
    public @NotBlank String getPassword() {
        return password;
    }

    @Nonnull
    @JsonProperty("birthday")
    public @NotNull Date getBirthday() {
        return birthday;
    }
}
