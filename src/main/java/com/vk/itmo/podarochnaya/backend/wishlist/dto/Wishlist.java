package com.vk.itmo.podarochnaya.backend.wishlist.dto;

import com.vk.itmo.podarochnaya.backend.wishlist.jpa.WishlistStatus;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Wishlist {
    private Long id;
    private String title;
    private String description;
    private WishlistStatus status;
    private String visibility;
    private Long ownerUserId;
    private LocalDateTime createdAt;
}
