package com.vk.itmo.podarochnaya.backend.santa.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SantaPairRepository extends JpaRepository<SantaPairEntity, Long> {
    List<SantaPairEntity> findByIdIn(List<Long> ids);
}
