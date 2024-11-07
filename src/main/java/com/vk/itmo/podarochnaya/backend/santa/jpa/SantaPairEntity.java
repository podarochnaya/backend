package com.vk.itmo.podarochnaya.backend.santa.jpa;

import com.vk.itmo.podarochnaya.backend.common.jpa.BaseEntity;
import com.vk.itmo.podarochnaya.backend.user.jpa.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "santa_pair")
@Data
public class SantaPairEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "giver_user_id")
    private UserEntity giver;

    @ManyToOne
    @JoinColumn(name = "taker_user_id")
    private UserEntity taker;

    @ManyToOne
    @JoinColumn(name = "santa_group_id")
    private SantaGroupEntity santaGroup;

    @Column(name = "deleted")
    private boolean deleted;
}
