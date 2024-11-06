package com.vk.itmo.podarochnaya.backend.wishlist.dto;

import lombok.Data;

@Data
public class WishlistUpdateRequest {
    private String title;
    private String description;
    private String status;
    private String visibility;
    private Long ownerUserId;
}
