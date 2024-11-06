package com.vk.itmo.podarochnaya.backend.wishlist.mapper;

import com.vk.itmo.podarochnaya.backend.common.jpa.BaseEntity;
import com.vk.itmo.podarochnaya.backend.user.jpa.UserEntity;
import com.vk.itmo.podarochnaya.backend.wishlist.dto.Wishlist;
import com.vk.itmo.podarochnaya.backend.wishlist.jpa.WishlistEntity;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import static com.vk.itmo.podarochnaya.backend.common.utils.Utils.getIdList;

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
    @Mapping(source = "allowedUsers", target = "allowedUserIds", qualifiedByName = "usersToIds")
    Wishlist toWishlist(WishlistEntity wishlist);

    List<Wishlist> toWishlists(List<WishlistEntity> wishlistEntities);

    @Named("usersToIds")
    default List<Long> mapUserEntitySetToIdList(Collection<UserEntity> users) {
        return getIdList(users);
    }
}
