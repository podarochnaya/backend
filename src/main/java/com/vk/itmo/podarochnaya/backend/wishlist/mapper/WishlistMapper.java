package com.vk.itmo.podarochnaya.backend.wishlist.mapper;

import com.vk.itmo.podarochnaya.backend.user.dto.UserRef;
import com.vk.itmo.podarochnaya.backend.user.jpa.UserEntity;
import com.vk.itmo.podarochnaya.backend.wishlist.dto.GiftRef;
import com.vk.itmo.podarochnaya.backend.wishlist.dto.Wishlist;
import com.vk.itmo.podarochnaya.backend.wishlist.jpa.GiftEntity;
import com.vk.itmo.podarochnaya.backend.wishlist.jpa.WishlistEntity;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WishlistMapper {

    default LocalDateTime mapInstantToLocalDateTime(Instant instant) {
        if (instant == null) {
            return null;
        }
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    }

    GiftMapper INSTANCE = Mappers.getMapper(GiftMapper.class);

    @Mapping(source = "owner", target = "ownerUser", qualifiedByName = "userToRef")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "allowedUsers", target = "allowedUsers", qualifiedByName = "usersToRefs")
    @Mapping(source = "gifts", target = "gifts", qualifiedByName = "giftsToRefs")
    Wishlist toWishlist(WishlistEntity wishlist);

    List<Wishlist> toWishlists(List<WishlistEntity> wishlistEntities);

    @Named("usersToRefs")
    default List<UserRef> mapUserEntitySetToRefList(Collection<UserEntity> users) {
        if (users == null) {
            return Collections.emptyList();
        }

        return users.stream()
            .map(UserEntity::toRef)
            .toList();
    }

    @Named("giftsToRefs")
    default List<GiftRef> mapGiftEntitySetToRefList(Collection<GiftEntity> giftEntities) {
        if (giftEntities == null) {
            return Collections.emptyList();
        }

        return giftEntities.stream()
            .map(GiftEntity::toRef)
            .toList();
    }

    @Named("userToRef")
    default UserRef mapUserEntityToRef(UserEntity user) {
        return user.toRef();
    }
}
