package com.vk.itmo.podarochnaya.backend.santa.dto;

import com.vk.itmo.podarochnaya.backend.user.dto.UserRef;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class SantaGroup {
    private String title;

    private UserRef owner;

    private Date expiration;

    private SantaGroupStatus status;

    private List<SantaPair> santaPairs;

    private List<UserRef> users;
}
