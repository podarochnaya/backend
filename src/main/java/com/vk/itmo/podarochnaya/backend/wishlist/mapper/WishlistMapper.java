package com.vk.itmo.podarochnaya.backend.wishlist.mapper;

import com.vk.itmo.podarochnaya.backend.wishlist.dto.Wishlist;
import com.vk.itmo.podarochnaya.backend.wishlist.jpa.WishlistEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(componentModel = "spring")
public interface WishlistMapper {

    default LocalDateTime mapInstantToLocalDateTime(Instant instant) {
        if (instant == null) {
            return null;
        }
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    }

    GiftMapper INSTANCE = Mappers.getMapper(GiftMapper.class);

    @Mapping(source = "owner.id", target = "ownerUserId")
    @Mapping(source = "createdAt", target = "createdAt")
    Wishlist toWishlist(WishlistEntity wishlist);

    List<Wishlist> toWishlists(List<WishlistEntity> wishlistEntities);
}