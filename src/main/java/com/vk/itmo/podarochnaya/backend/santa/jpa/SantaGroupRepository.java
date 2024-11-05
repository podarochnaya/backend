package com.vk.itmo.podarochnaya.backend.santa.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SantaGroupRepository extends JpaRepository<SantaGroupEntity, Long> {
    // No-op.
}
