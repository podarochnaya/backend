package com.vk.itmo.podarochnaya.backend.wishlist.dto;

import com.vk.itmo.podarochnaya.backend.wishlist.jpa.WishlistStatus;
import com.vk.itmo.podarochnaya.backend.wishlist.jpa.WishlistVisibility;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;

@Data
public class WishlistUpdateRequest {
    @Size(max = 255, message = "Title must be at most 255 characters")
    private String title;

    @Size(max = 500, message = "Description must be at most 500 characters")
    private String description;

    private WishlistStatus status;

    private WishlistVisibility visibility;

    private List<@Email String> allowedUserEmails;
}
