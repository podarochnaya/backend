package com.vk.itmo.podarochnaya.backend.wishlist.jpa;

import com.vk.itmo.podarochnaya.backend.common.jpa.BaseEntity;
import com.vk.itmo.podarochnaya.backend.user.jpa.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

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
    private int status;

    @ManyToOne
    @JoinColumn(name = "owner_user_id")
    private UserEntity owner;

    @Column(name = "visibility")
    private int visibility;

    @OneToMany(mappedBy = "wishlist")
    private List<GiftEntity> gifts;

    @ManyToMany(mappedBy = "wishlists")
    private Set<UserEntity> users;
}
