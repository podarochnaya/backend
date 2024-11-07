package com.vk.itmo.podarochnaya.backend.wishlist.dto;

import lombok.Data;

@Data
public class GiftWithImageResponse {
    private Gift gift;
    private String image;
}
