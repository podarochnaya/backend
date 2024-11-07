package com.vk.itmo.podarochnaya.backend.santa.mapper;

import com.vk.itmo.podarochnaya.backend.santa.dto.SantaGroup;
import com.vk.itmo.podarochnaya.backend.santa.dto.SantaGroupRef;
import com.vk.itmo.podarochnaya.backend.santa.dto.SantaGroupStatus;
import com.vk.itmo.podarochnaya.backend.santa.dto.SantaPair;
import com.vk.itmo.podarochnaya.backend.santa.jpa.SantaGroupEntity;
import com.vk.itmo.podarochnaya.backend.santa.jpa.SantaPairEntity;
import com.vk.itmo.podarochnaya.backend.user.dto.UserRef;
import com.vk.itmo.podarochnaya.backend.user.jpa.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class SantaGroupMapper {
    public static SantaGroup map(SantaGroupEntity entity) {
        return SantaGroup.builder()
                .id(entity.getId())
                .owner(map(entity.getOwner()))
                .title(entity.getTitle())
                .users(entity.getUsers().stream().map(SantaGroupMapper::map).toList())
                .expiration(entity.getExpiration())
                .santaPairs(entity.getSantaPairs().stream().map(SantaGroupMapper::map).toList())
                .status(SantaGroupStatus.fromStatus(entity.getStatus()))
                .build();
    }

    public static UserRef map(UserEntity entity) {
        return UserRef.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .build();
    }

    public static SantaPair map(SantaPairEntity entity) {
        return SantaPair.builder()
                .id(entity.getId())
                .santaGroup(SantaGroupRef.builder()
                        .id(entity.getSantaGroup().getId())
                        .title(entity.getSantaGroup().getTitle())
                        .build())
                .giver(map(entity.getGiver()))
                .taker(map(entity.getTaker()))
                .deleted(entity.isDeleted())
                .build();
    }
}
