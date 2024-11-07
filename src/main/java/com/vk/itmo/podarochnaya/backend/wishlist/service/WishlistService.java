package com.vk.itmo.podarochnaya.backend.wishlist.service;

import com.vk.itmo.podarochnaya.backend.exception.NotFoundException;
import com.vk.itmo.podarochnaya.backend.user.jpa.UserEntity;
import com.vk.itmo.podarochnaya.backend.user.service.UserService;
import com.vk.itmo.podarochnaya.backend.wishlist.dto.Wishlist;
import com.vk.itmo.podarochnaya.backend.wishlist.dto.WishlistCreateRequest;
import com.vk.itmo.podarochnaya.backend.wishlist.dto.WishlistUpdateRequest;
import com.vk.itmo.podarochnaya.backend.wishlist.jpa.WishlistEntity;
import com.vk.itmo.podarochnaya.backend.wishlist.jpa.WishlistRepository;
import com.vk.itmo.podarochnaya.backend.wishlist.mapper.WishlistMapper;
import java.util.List;
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

        WishlistEntity wishlistEntity = wishlistRepository.save(wishlist);
        return mapper.toWishlist(wishlistEntity);
    }

    public List<Wishlist> getAllWishlists() {
        return mapper.toWishlists(wishlistRepository.findAll());
    }

    public Wishlist updateWishlist(Long wishlistId, WishlistUpdateRequest wishlistUpdateRequest) {
        WishlistEntity wishlist = getWishlistById(wishlistId);

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

        return mapper.toWishlist(wishlistRepository.save(wishlist));
    }

    public WishlistEntity getWishlistById(Long wishlistId) {
        return wishlistRepository.findById(wishlistId)
            .orElseThrow(() -> new NotFoundException("Cannot find wishlist with ID: " + wishlistId));
    }


    public boolean deleteWishlist(Long wishlistId) {
        Optional<WishlistEntity> existingWishlist = wishlistRepository.findById(wishlistId);
        if (existingWishlist.isPresent()) {
            wishlistRepository.deleteById(wishlistId);
            return true;
        }
        return false;
    }
}
