package com.vk.itmo.podarochnaya.backend.wishlist.service;

import com.vk.itmo.podarochnaya.backend.exception.AccessDeniedRuntimeException;
import com.vk.itmo.podarochnaya.backend.exception.NotFoundException;
import com.vk.itmo.podarochnaya.backend.user.jpa.UserEntity;
import com.vk.itmo.podarochnaya.backend.user.service.UserService;
import com.vk.itmo.podarochnaya.backend.wishlist.dto.Wishlist;
import com.vk.itmo.podarochnaya.backend.wishlist.dto.WishlistCreateRequest;
import com.vk.itmo.podarochnaya.backend.wishlist.dto.WishlistUpdateRequest;
import com.vk.itmo.podarochnaya.backend.wishlist.jpa.WishlistEntity;
import com.vk.itmo.podarochnaya.backend.wishlist.jpa.WishlistRepository;
import com.vk.itmo.podarochnaya.backend.wishlist.mapper.WishlistMapper;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final UserService userService;
    private final WishlistMapper mapper;

    public Wishlist createWishlist(WishlistCreateRequest wishlistCreateRequest) {
        UserEntity user = userService.getById(wishlistCreateRequest.getOwnerUserId());

        WishlistEntity wishlist = new WishlistEntity();
        wishlist.setTitle(wishlistCreateRequest.getTitle().trim());
        wishlist.setDescription(wishlistCreateRequest.getDescription().trim());
        wishlist.setStatus(wishlistCreateRequest.getStatus());
        wishlist.setOwner(user);
        wishlist.setVisibility(wishlistCreateRequest.getVisibility());
        wishlist.setAllowedUsers(new HashSet<>(userService.getByIds(wishlistCreateRequest.getAllowedUserIds())));

        WishlistEntity wishlistEntity = wishlistRepository.save(wishlist);
        return mapper.toWishlist(wishlistEntity);
    }

    public List<Wishlist> getAllWishlists() {
        var currentUser = userService.getAuthenticatedUser();

        return mapper.toWishlists(wishlistRepository.findAllAccessibleWishlists(currentUser.getId()));
    }

    public Wishlist updateWishlist(Long wishlistId, WishlistUpdateRequest wishlistUpdateRequest) {
        WishlistEntity wishlist = getWishlistById(wishlistId);

        checkOwner(wishlist);

        if (wishlistUpdateRequest.getOwnerUserId() != null) {
            UserEntity user = userService.getById(wishlistUpdateRequest.getOwnerUserId());

            wishlist.setOwner(user);
        }

        if (wishlistUpdateRequest.getTitle() != null) {
            wishlist.setTitle(wishlistUpdateRequest.getTitle().trim());
        }

        if (wishlistUpdateRequest.getDescription() != null) {
            wishlist.setDescription(wishlistUpdateRequest.getDescription().trim());
        }

        if (wishlistUpdateRequest.getStatus() != null) {
            wishlist.setStatus(wishlistUpdateRequest.getStatus());
        }

        if (wishlistUpdateRequest.getVisibility() != null) {
            wishlist.setVisibility(wishlistUpdateRequest.getVisibility());
        }

        if (wishlistUpdateRequest.getAllowedUserIds() != null) {
            wishlist.setAllowedUsers(
                new HashSet<>(
                    userService.getByIds(wishlistUpdateRequest.getAllowedUserIds())
                )
            );
        }

        return mapper.toWishlist(wishlistRepository.save(wishlist));
    }

    private void checkOwner(WishlistEntity wishlist) {
        var currentUser = userService.getAuthenticatedUser();

        if (!Objects.equals(wishlist.getOwner().getId(), currentUser.getId())) {
            throw new AccessDeniedRuntimeException(currentUser.getEmail() + " is not the owner of wishlist " + wishlist.getId());
        }
    }

    public List<WishlistEntity> getWishlistsByIds(List<Long> wishlistIds) {
        var currentUser = userService.getAuthenticatedUser();

        return wishlistRepository.findAccessibleWishlistsByIds(wishlistIds, currentUser.getId());
    }

    public WishlistEntity getWishlistById(Long wishlistId) {
        var currentUser = userService.getAuthenticatedUser();

        return wishlistRepository.findAccessibleWishlistsByIds(List.of(wishlistId), currentUser.getId()).stream().findFirst()
            .orElseThrow(() -> new NotFoundException("Cannot find or forbidden access to wishlist with ID: " + wishlistId));
    }

    public boolean deleteWishlist(Long wishlistId) {
        Optional<WishlistEntity> existingWishlist = wishlistRepository.findById(wishlistId);

        if (existingWishlist.isPresent()) {
            checkOwner(existingWishlist.get());

            wishlistRepository.deleteById(wishlistId);

            return true;
        }
        return false;
    }
}
