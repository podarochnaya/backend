package com.vk.itmo.podarochnaya.backend.santa.dto;

import lombok.Data;

@Data
public class SantaPairRequest {
    private Long giverUserId;

    private Long takerUserId;

    private Long santaGroupId;

    private Boolean deleted;
}
