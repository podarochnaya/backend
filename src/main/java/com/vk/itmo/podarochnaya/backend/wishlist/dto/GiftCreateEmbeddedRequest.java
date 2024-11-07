package com.vk.itmo.podarochnaya.backend.wishlist.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GiftCreateEmbeddedRequest extends GiftCreateBaseRequest {
    @NotNull
    private FileBase64Dto file;
}
