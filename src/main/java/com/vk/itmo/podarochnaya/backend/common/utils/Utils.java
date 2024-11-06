package com.vk.itmo.podarochnaya.backend.common.utils;

import com.vk.itmo.podarochnaya.backend.common.jpa.BaseEntity;
import com.vk.itmo.podarochnaya.backend.user.jpa.UserEntity;
import java.util.Collection;
import java.util.List;

public class Utils {
    public static List<Long> getIdList(Collection<UserEntity> users) {
        return users.stream()
            .map(BaseEntity::getId)
            .toList();
    }
}
