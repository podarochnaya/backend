package com.vk.itmo.podarochnaya.backend.santa.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SantaPairRepository extends JpaRepository<SantaPairEntity, Long> {
    // No-op.
}
