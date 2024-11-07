package com.vk.itmo.podarochnaya.backend.wishlist.dto;

import com.vk.itmo.podarochnaya.backend.user.dto.UserRef;
import com.vk.itmo.podarochnaya.backend.wishlist.jpa.GiftStatus;
import com.vk.itmo.podarochnaya.backend.wishlist.jpa.GiftVisibility;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class Gift {
    private Long id;
    private String title;
    private String description;
    private String url;
    private BigDecimal price;
    private boolean reserved;
    private WishlistRef wishlist;
    private UserRef reserver;
    private GiftStatus status;
    private LocalDateTime createdAt;
    private List<Long> allowedUserIds;
    private GiftVisibility visibility;
}
