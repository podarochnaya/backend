package com.vk.itmo.podarochnaya.backend.wishlist.dto;

import com.vk.itmo.podarochnaya.backend.user.dto.UserRef;
import com.vk.itmo.podarochnaya.backend.wishlist.jpa.WishlistStatus;
import com.vk.itmo.podarochnaya.backend.wishlist.jpa.WishlistVisibility;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class Wishlist {
    private Long id;
    private String title;
    private String description;
    private WishlistStatus status;
    private WishlistVisibility visibility;
    private UserRef ownerUser;
    private LocalDateTime createdAt;
    private List<UserRef> allowedUsers;
}
