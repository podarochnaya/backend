package com.vk.itmo.podarochnaya.backend.wishlist.jpa;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WishlistRepository extends JpaRepository<WishlistEntity, Long> {
    @Query("""
        SELECT w
        FROM WishlistEntity w
        WHERE w.id IN :wishlistIds
        AND (
            w.visibility = com.vk.itmo.podarochnaya.backend.wishlist.jpa.WishlistVisibility.PUBLIC
            OR
            EXISTS (
                SELECT 1
                FROM UserEntity u
                WHERE u.id = :userId AND u MEMBER OF w.allowedUsers
            )
        )
        """)
    List<WishlistEntity> findAccessibleWishlistsByIds(@Param("wishlistIds") List<Long> wishlistIds, @Param("userId") long userId);

    @Query("""
        SELECT w
        FROM WishlistEntity w
        WHERE
        w.visibility = com.vk.itmo.podarochnaya.backend.wishlist.jpa.WishlistVisibility.PUBLIC
        OR
        EXISTS (
            SELECT 1
            FROM UserEntity u
            WHERE u.id = :userId AND u MEMBER OF w.allowedUsers
        )
        """)
        //TODO Refactor to use user id instead if email
    List<WishlistEntity> findAllAccessibleWishlists(@Param("userId") long userId);
}
