package com.vk.itmo.podarochnaya.backend.wishlist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GiftRef {
    private Long id;
    private String title;
}
