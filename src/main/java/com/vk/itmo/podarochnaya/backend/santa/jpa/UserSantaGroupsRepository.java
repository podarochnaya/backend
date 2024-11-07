package com.vk.itmo.podarochnaya.backend.santa.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSantaGroupsRepository extends JpaRepository<UsersSantaGroupEntity, Long> {
}
