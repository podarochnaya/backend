package com.vk.itmo.podarochnaya.backend.wishlist.mapper;

import com.vk.itmo.podarochnaya.backend.wishlist.dto.Gift;
import com.vk.itmo.podarochnaya.backend.wishlist.jpa.GiftEntity;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GiftMapper {

    default LocalDateTime mapInstantToLocalDateTime(Instant instant) {
        if (instant == null) {
            return null;
        }
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    }

    GiftMapper INSTANCE = Mappers.getMapper(GiftMapper.class);

    @Mapping(source = "wishlist.id", target = "wishlist.id")
    @Mapping(source = "wishlist.title", target = "wishlist.title")
    @Mapping(source = "reserver.id", target = "reserver.id")
    @Mapping(source = "reserver.email", target = "reserver.email")
    @Mapping(source = "createdAt", target = "createdAt")
    Gift toGift(GiftEntity giftEntity);

    List<Gift> toGiftList(List<GiftEntity> giftEntities);
}
