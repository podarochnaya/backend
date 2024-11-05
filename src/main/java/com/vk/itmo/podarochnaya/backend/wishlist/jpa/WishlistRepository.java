package com.vk.itmo.podarochnaya.backend.wishlist.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<WishlistEntity, Long> {
    // No-op.
}
