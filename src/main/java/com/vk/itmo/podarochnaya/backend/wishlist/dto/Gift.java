package com.vk.itmo.podarochnaya.backend.wishlist.dto;

import com.vk.itmo.podarochnaya.backend.user.dto.UserRef;
import com.vk.itmo.podarochnaya.backend.wishlist.jpa.GiftStatus;
import java.time.LocalDateTime;
import lombok.Data;

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
    private GiftStatus status;
    private LocalDateTime createdAt;
}
