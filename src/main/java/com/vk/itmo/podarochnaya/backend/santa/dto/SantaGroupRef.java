package com.vk.itmo.podarochnaya.backend.santa.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SantaGroupRef {
    private final long id;
    
    private final String title;
}
