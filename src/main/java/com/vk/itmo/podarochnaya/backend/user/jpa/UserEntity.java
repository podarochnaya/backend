package com.vk.itmo.podarochnaya.backend.user.jpa;

import com.vk.itmo.podarochnaya.backend.common.jpa.BaseEntity;
import com.vk.itmo.podarochnaya.backend.santa.jpa.SantaGroupEntity;
import com.vk.itmo.podarochnaya.backend.santa.jpa.SantaPairEntity;
import com.vk.itmo.podarochnaya.backend.user.dto.UserRef;
import com.vk.itmo.podarochnaya.backend.wishlist.jpa.GiftEntity;
import com.vk.itmo.podarochnaya.backend.wishlist.jpa.WishlistEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@Entity
@Builder
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends BaseEntity implements UserDetails {
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "birthday")
    private Date birthday;

    @ManyToMany(mappedBy = "allowedUsers", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<WishlistEntity> visibleWishlists;

    @ManyToMany(mappedBy = "allowedUsers", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<GiftEntity> visibleGifts;

    @ManyToMany(mappedBy = "users", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<SantaGroupEntity> santaGroups;

    @OneToMany(mappedBy = "owner")
    private List<WishlistEntity> ownedWishlists;

    @OneToMany(mappedBy = "owner")
    private List<SantaGroupEntity> ownedSantaGroups;

    @OneToMany(mappedBy = "giver")
    private List<SantaPairEntity> givingPairs;

    @OneToMany(mappedBy = "taker")
    private List<SantaPairEntity> takingPairs;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public UserRef toRef() {
        return new UserRef(
            this.getId(),
            this.getEmail()
        );
    }
}
