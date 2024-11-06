package com.vk.itmo.podarochnaya.backend.wishlist.dto;

import lombok.Data;

@Data
public class GiftUpdateRequest {
    private String title;
    private String description;
    private String url;
    private Double price;
    private Boolean reserved;
    private Long reserverUserId;
    private String photoId;
    private Integer status;
}
