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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
