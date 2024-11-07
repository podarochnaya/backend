package com.vk.itmo.podarochnaya.backend.santa.dto;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import lombok.Data;

@Data
public class SantaGroupRequest {
    private String title;

    private Long ownerUserId;

    private Date expiration;

    private String status;

    private List<Long> santaPairIds;

    private List<Long> userIds;

    public Optional<String> getTitleOpt() {
        return Optional.ofNullable(title);
    }

    public Optional<Long> getOwnerUserIdOpt() {
        return Optional.ofNullable(ownerUserId);
    }

    public Optional<Date> getExpirationOpt() {
        return Optional.ofNullable(expiration);
    }

    public Optional<String> getStatusOpt() {
        return Optional.ofNullable(status);
    }

    public List<Long> getSantaPairIds() {
        return santaPairIds == null ? List.of() : santaPairIds;
    }

    public List<Long> getUserIds() {
        return userIds == null ? List.of() : userIds;
    }
}
