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
                WHERE u.email = :userEmail AND u MEMBER OF g.allowedUsers
            )
        )
        """)
        //TODO Refactor to use user id instead if email
    List<GiftEntity> findAccessibleGiftsByIds(@Param("giftIds") List<Long> giftIds, @Param("userEmail") String userEmail);

    @Query("""
        SELECT g
        FROM GiftEntity g
        WHERE
        g.visibility = com.vk.itmo.podarochnaya.backend.wishlist.jpa.GiftVisibility.PUBLIC
        OR
        EXISTS (
            SELECT 1
            FROM UserEntity u
            WHERE u.email = :userEmail AND u MEMBER OF g.allowedUsers
        )
        """)
        //TODO Refactor to use user id instead if email
    List<GiftEntity> findAllAccessibleGifts(@Param("userEmail") String userEmail);
}
