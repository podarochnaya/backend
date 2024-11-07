package com.vk.itmo.podarochnaya.backend.common.utils;

import com.vk.itmo.podarochnaya.backend.common.jpa.BaseEntity;
import com.vk.itmo.podarochnaya.backend.user.jpa.UserEntity;
import java.util.Collection;
import java.util.List;

public class Utils {
    private Utils() {
        // No-op.
    }

    public static List<Long> getIdList(Collection<? extends BaseEntity> entities) {
        return entities.stream()
            .map(BaseEntity::getId)
            .toList();
    }
}
