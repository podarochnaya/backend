package com.vk.itmo.podarochnaya.backend.user.jpa;

import com.vk.itmo.podarochnaya.backend.common.jpa.BaseEntity;
import com.vk.itmo.podarochnaya.backend.santa.jpa.SantaGroupEntity;
import com.vk.itmo.podarochnaya.backend.santa.jpa.SantaPairEntity;
import com.vk.itmo.podarochnaya.backend.wishlist.jpa.WishlistEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "user")
public class UserEntity extends BaseEntity {
    @Column(name = "username")
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "birthday")
    private Date birthday;

    @ManyToMany
    @JoinTable(
        name = "users_wishlists",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "wishlist_id")
    )
    private Set<WishlistEntity> wishlists;

    @ManyToMany
    @JoinTable(
        name = "users_santa_groups",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "santa_group_id")
    )
    private Set<SantaGroupEntity> santaGroups;

    @OneToMany(mappedBy = "owner")
    private List<WishlistEntity> ownedWishlists;

    @OneToMany(mappedBy = "owner")
    private List<SantaGroupEntity> ownedSantaGroups;

    @OneToMany(mappedBy = "giver")
    private List<SantaPairEntity> givingPairs;

    @OneToMany(mappedBy = "taker")
    private List<SantaPairEntity> takingPairs;
}
