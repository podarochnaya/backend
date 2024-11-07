package com.vk.itmo.podarochnaya.backend.wishlist.jpa;

import com.vk.itmo.podarochnaya.backend.common.jpa.BaseEntity;
import com.vk.itmo.podarochnaya.backend.user.jpa.UserEntity;
import com.vk.itmo.podarochnaya.backend.wishlist.dto.GiftRef;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "gift")
public class GiftEntity extends BaseEntity {
    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "url")
    private String url;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "reserved")
    private boolean reserved;

    @Column(name = "photo_id")
    private String photoId;

    @ManyToOne
    @JoinColumn(name = "wishlist_id")
    private WishlistEntity wishlist;

    @ManyToOne
    @JoinColumn(name = "reserver_user_id")
    private UserEntity reserver;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private GiftStatus status;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
        name = "users_gifts",
        joinColumns = @JoinColumn(name = "gift_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserEntity> allowedUsers;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility")
    private GiftVisibility visibility;

    public GiftRef toRef() {
        return new GiftRef(this.getId(), this.getTitle());
    }
}
