package com.vk.itmo.podarochnaya.backend.wishlist.jpa;

import com.vk.itmo.podarochnaya.backend.common.jpa.BaseEntity;
import com.vk.itmo.podarochnaya.backend.user.jpa.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "wishlist")
public class WishlistEntity extends BaseEntity {
    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "owner_user_id")
    private UserEntity owner;

    @Column(name = "visibility")
    private String visibility;

    @OneToMany(mappedBy = "wishlist")
    private List<GiftEntity> gifts;

    @ManyToMany(mappedBy = "wishlists")
    private Set<UserEntity> users;
}
