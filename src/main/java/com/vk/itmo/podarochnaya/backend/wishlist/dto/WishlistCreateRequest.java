package com.vk.itmo.podarochnaya.backend.wishlist.dto;

import com.vk.itmo.podarochnaya.backend.wishlist.jpa.WishlistStatus;
import com.vk.itmo.podarochnaya.backend.wishlist.jpa.WishlistVisibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;

@Data
public class WishlistCreateRequest {
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must be at most 255 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description must be at most 500 characters")
    private String description;

    @NotNull(message = "Status is required")
    private WishlistStatus status;

    @NotNull(message = "Owner user ID is required")
    private Long ownerUserId;

    @NotNull(message = "Visibility is required")
    private WishlistVisibility visibility;

    private List<Long> allowedUserIds;
}
