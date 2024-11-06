package com.vk.itmo.podarochnaya.backend.wishlist.dto;

import lombok.Data;

@Data
public class WishlistCreateRequest {
    private String title;
    private String description;
    private String status;
    private Long ownerUserId;
    private String visibility;
}
