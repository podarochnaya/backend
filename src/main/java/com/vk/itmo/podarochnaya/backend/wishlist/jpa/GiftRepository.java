package com.vk.itmo.podarochnaya.backend.wishlist.jpa;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GiftRepository extends JpaRepository<GiftEntity, Long> {
    @Query("""
        SELECT g
        FROM GiftEntity g
        WHERE g.id IN :giftIds
        AND (
            g.visibility = com.vk.itmo.podarochnaya.backend.wishlist.jpa.GiftVisibility.PUBLIC
            OR
            EXISTS (
                SELECT 1
                FROM UserEntity u
                LEFT JOIN WishlistEntity w ON u = w.owner
                WHERE u.id = :userId
                AND (u MEMBER OF g.allowedUsers OR g MEMBER OF w.gifts)
            )
        )
        """)
    List<GiftEntity> findAccessibleGiftsByIds(@Param("giftIds") List<Long> giftIds, @Param("userId") long userId);

    @Query("""
        SELECT g
        FROM GiftEntity g
        WHERE
        g.visibility = com.vk.itmo.podarochnaya.backend.wishlist.jpa.GiftVisibility.PUBLIC
        OR
        EXISTS (
                SELECT 1
                FROM UserEntity u
                LEFT JOIN WishlistEntity w ON u = w.owner
                WHERE u.id = :userId
                AND (u MEMBER OF g.allowedUsers OR g MEMBER OF w.gifts)
        )
        """)
    List<GiftEntity> findAllAccessibleGifts(@Param("userId") long userId);
}
