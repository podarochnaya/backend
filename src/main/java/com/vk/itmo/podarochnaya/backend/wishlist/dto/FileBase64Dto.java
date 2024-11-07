package com.vk.itmo.podarochnaya.backend.wishlist.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileBase64Dto {
    @NotBlank
    private String fileName;

    @NotBlank
    private String fileContentBase64;

    @NotBlank
    private String contentType;
}
