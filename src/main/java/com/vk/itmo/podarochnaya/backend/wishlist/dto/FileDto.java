package com.vk.itmo.podarochnaya.backend.wishlist.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {
    @NotBlank
    private String fileName;

    private byte[] fileContent;

    @NotBlank
    private String contentType;
}
