package com.vk.itmo.podarochnaya.backend.santa.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SantaPairCreateRequest {
    @NotNull(message = "Giver user ID cannot be null")
    @Positive(message = "Giver user ID must be positive")
    private Long giverUserId;

    @NotNull(message = "Taker user ID cannot be null")
    @Positive(message = "Taker user ID must be positive")
    private Long takerUserId;

    @NotNull(message = "Santa group ID cannot be null")
    @Positive(message = "Santa group ID must be positive")
    private Long santaGroupId;
}
