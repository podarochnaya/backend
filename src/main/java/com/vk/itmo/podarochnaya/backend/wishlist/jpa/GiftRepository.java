package com.vk.itmo.podarochnaya.backend.wishlist.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftRepository extends JpaRepository<GiftEntity, Long> {
    // No-op.
}
