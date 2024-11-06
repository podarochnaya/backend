package com.vk.itmo.podarochnaya.backend.wishlist.dto;

import lombok.Data;

@Data
public class GiftCreateRequest {
    private String title;
    private String description;
    private String url;
    private double price;
    private boolean reserved;
    private String photoId;
    private Long wishlistId;
    private Long reserverUserId;
    private int status;
}
