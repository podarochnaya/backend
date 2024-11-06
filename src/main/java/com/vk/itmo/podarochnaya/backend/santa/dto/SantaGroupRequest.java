package com.vk.itmo.podarochnaya.backend.santa.dto;

import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class SantaGroupRequest {
    private String title;

    private Long ownerUserId;

    private Date expiration;

    private String status;

    private List<Long> santaPairIds;

    private List<Long> userIds;
}
