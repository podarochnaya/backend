package com.vk.itmo.podarochnaya.backend.santa.dto;

import com.vk.itmo.podarochnaya.backend.user.dto.UserRef;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class SantaGroup {
    private String title;

    private UserRef owner;

    private Date expiration;

    private String status;

    private List<SantaPair> santaPairs;

    private List<UserRef> users;
}
