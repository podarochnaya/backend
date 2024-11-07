package com.vk.itmo.podarochnaya.backend.santa.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users_santa_groups")
@IdClass(UsersSantaGroupId.class)
public class UsersSantaGroupEntity {
    @Id
    @Column(name = "user_id")
    private long userId;

    @Id
    @Column(name = "santa_group_id")
    private long santaGroupId;
}
