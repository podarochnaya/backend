package com.vk.itmo.podarochnaya.backend.wishlist.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Wishlist {
    private Long id;
    private String title;
    private String description;
    private String status;
    private String visibility;
    private Long ownerUserId;
    private LocalDateTime createdAt;
}
