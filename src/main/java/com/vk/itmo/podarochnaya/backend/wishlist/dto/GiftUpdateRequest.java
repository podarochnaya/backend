package com.vk.itmo.podarochnaya.backend.wishlist.dto;

import com.vk.itmo.podarochnaya.backend.wishlist.jpa.GiftStatus;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class GiftUpdateRequest {
    @Size(max = 255, message = "Title must be less than 255 characters")
    private String title;

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    @Size(max = 255, message = "URL must be less than 255 characters")
    @Pattern(regexp = "^(https?://.*)?$", message = "URL must be a valid HTTP/HTTPS URL")
    private String url;

    @PositiveOrZero(message = "Price must be zero or positive")
    @Digits(integer = 10, fraction = 2, message = "Price must have up to 10 digits and 2 decimal places")
    private BigDecimal price;

    private Boolean reserved;

    private Long reserverUserId;

    @Size(max = 100, message = "Photo ID must be less than 100 characters")
    private String photoId;

    private GiftStatus status;
}
