package com.vk.itmo.podarochnaya.backend.santa.jpa;

import com.vk.itmo.podarochnaya.backend.common.jpa.BaseEntity;
import com.vk.itmo.podarochnaya.backend.user.jpa.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "santa_group")
public class SantaGroupEntity extends BaseEntity {
    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

    @Column(name = "expiration", nullable = false)
    private Date expiration;

    @Column(name = "status")
    private int status;

    @OneToMany(mappedBy = "santaGroup")
    private List<SantaPairEntity> santaPairs;

    @ManyToMany(mappedBy = "santaGroups")
    private Set<UserEntity> users;
}
