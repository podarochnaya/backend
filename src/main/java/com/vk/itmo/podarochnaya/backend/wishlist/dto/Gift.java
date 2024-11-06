package com.vk.itmo.podarochnaya.backend.wishlist.dto;

import com.vk.itmo.podarochnaya.backend.user.dto.UserRef;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Gift {
    private Long id;
    private String title;
    private String description;
    private String url;
    private double price;
    private boolean reserved;
    private String photoId;
    private WishlistRef wishlist;
    private UserRef reserver;
    private int status;
    private LocalDateTime createdAt;
}
