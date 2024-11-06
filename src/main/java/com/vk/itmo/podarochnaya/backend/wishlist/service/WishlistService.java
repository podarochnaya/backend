package com.vk.itmo.podarochnaya.backend.wishlist.service;

import com.vk.itmo.podarochnaya.backend.user.jpa.UserEntity;
import com.vk.itmo.podarochnaya.backend.user.jpa.UserRepository;
import com.vk.itmo.podarochnaya.backend.wishlist.dto.Wishlist;
import com.vk.itmo.podarochnaya.backend.wishlist.dto.WishlistCreateRequest;
import com.vk.itmo.podarochnaya.backend.wishlist.dto.WishlistUpdateRequest;
import com.vk.itmo.podarochnaya.backend.wishlist.jpa.WishlistEntity;
import com.vk.itmo.podarochnaya.backend.wishlist.jpa.WishlistRepository;
import com.vk.itmo.podarochnaya.backend.wishlist.mapper.WishlistMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final WishlistMapper mapper;

    public Wishlist createWishlist(WishlistCreateRequest wishlistCreateRequest) {
        Optional<UserEntity> user = userRepository.findById(wishlistCreateRequest.getOwnerUserId());
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        WishlistEntity wishlist = new WishlistEntity();
        wishlist.setTitle(wishlistCreateRequest.getTitle());
        wishlist.setDescription(wishlistCreateRequest.getDescription());
        wishlist.setStatus(wishlistCreateRequest.getStatus());
        wishlist.setOwner(user.get());
        wishlist.setVisibility(wishlistCreateRequest.getVisibility());

        WishlistEntity wishlistEntity = wishlistRepository.save(wishlist);
        return mapper.toWishlist(wishlistEntity);
    }

    public List<Wishlist> getAllWishlists() {
        return mapper.toWishlists(wishlistRepository.findAll());
    }

    public Wishlist getWishlistById(Long wishlistId) {
        Optional<WishlistEntity> wishlist = wishlistRepository.findById(wishlistId);
        return wishlist.map(mapper::toWishlist).orElse(null);
    }

    public Wishlist updateWishlist(Long wishlistId, WishlistUpdateRequest wishlistUpdateRequest) {
        Optional<UserEntity> user = userRepository.findById(wishlistUpdateRequest.getOwnerUserId());
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        Optional<WishlistEntity> existingWishlist = wishlistRepository.findById(wishlistId);
        if (existingWishlist.isPresent()) {
            WishlistEntity wishlist = existingWishlist.get();
            wishlist.setTitle(wishlistUpdateRequest.getTitle());
            wishlist.setDescription(wishlistUpdateRequest.getDescription());
            wishlist.setStatus(wishlistUpdateRequest.getStatus());
            wishlist.setVisibility(wishlistUpdateRequest.getVisibility());
            wishlist.setOwner(user.get());
            return mapper.toWishlist(wishlistRepository.save(wishlist));
        }
        return null;
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
