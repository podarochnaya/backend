package com.vk.itmo.podarochnaya.backend.santa.dto;

import com.vk.itmo.podarochnaya.backend.user.dto.UserRef;
import lombok.Data;

@Data
public class SantaPair {
    private long id;

    private UserRef giver;

    private UserRef taker;

    private SantaGroupRef santaGroup;

    private boolean deleted;
}
