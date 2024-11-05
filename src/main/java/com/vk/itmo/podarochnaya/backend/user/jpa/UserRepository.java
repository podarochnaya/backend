package com.vk.itmo.podarochnaya.backend.user.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // No-op.
}
