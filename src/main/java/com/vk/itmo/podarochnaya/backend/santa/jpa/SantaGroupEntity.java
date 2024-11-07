package com.vk.itmo.podarochnaya.backend.santa.jpa;

import com.vk.itmo.podarochnaya.backend.common.jpa.BaseEntity;
import com.vk.itmo.podarochnaya.backend.user.jpa.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
        name = "users_santa_groups",
        joinColumns = @JoinColumn(name = "santa_group_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserEntity> users;
}
