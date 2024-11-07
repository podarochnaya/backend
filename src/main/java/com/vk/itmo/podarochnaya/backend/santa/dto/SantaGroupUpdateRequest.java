package com.vk.itmo.podarochnaya.backend.santa.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class SantaGroupUpdateRequest {
    @Size(max = 255, message = "Title must be at most 255 characters")
    private String title;

    private Long ownerUserId;

    @Future(message = "Expiration date must be in the future")
    private Date expiration;

    private SantaGroupStatus status;

    private List<Long> santaPairIds;

    private List<Long> userIds;
}
