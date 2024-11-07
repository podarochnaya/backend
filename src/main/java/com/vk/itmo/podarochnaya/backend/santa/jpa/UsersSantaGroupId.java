package com.vk.itmo.podarochnaya.backend.santa.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UsersSantaGroupId {
    private Long santaGroupId;
    private Long userId;
}
