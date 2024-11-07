package com.vk.itmo.podarochnaya.backend.wishlist.service;

import com.vk.itmo.podarochnaya.backend.exception.AccessDeniedRuntimeException;
import com.vk.itmo.podarochnaya.backend.exception.NotFoundException;
import com.vk.itmo.podarochnaya.backend.user.service.UserService;
import com.vk.itmo.podarochnaya.backend.wishlist.dto.FileDto;
import com.vk.itmo.podarochnaya.backend.wishlist.dto.Gift;
import com.vk.itmo.podarochnaya.backend.wishlist.dto.GiftCreateBaseRequest;
import com.vk.itmo.podarochnaya.backend.wishlist.dto.GiftUpdateRequest;
import com.vk.itmo.podarochnaya.backend.wishlist.dto.GiftWithImageResponse;
import com.vk.itmo.podarochnaya.backend.wishlist.jpa.GiftEntity;
import com.vk.itmo.podarochnaya.backend.wishlist.jpa.GiftRepository;
import com.vk.itmo.podarochnaya.backend.wishlist.jpa.GiftStatus;
import com.vk.itmo.podarochnaya.backend.wishlist.jpa.GiftVisibility;
import com.vk.itmo.podarochnaya.backend.wishlist.jpa.WishlistEntity;
import com.vk.itmo.podarochnaya.backend.wishlist.jpa.WishlistRepository;
import com.vk.itmo.podarochnaya.backend.wishlist.mapper.GiftMapper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GiftService {
    private final GiftRepository giftRepository;
    private final UserService userService;
    private final WishlistRepository wishlistRepository;
    private final GiftImageService giftImageService;
    private final GiftMapper giftMapper;

    @Transactional
    public Gift createGift(long wishlistId, GiftCreateBaseRequest giftCreateRequest, FileDto image) {
        WishlistEntity wishlist = getWishlistById(wishlistId);

        String photoUrl = null;
        if (image != null && !(image.getFileContent().length > 0)) {
            photoUrl = giftImageService.uploadGiftImage(image);
        }

        GiftEntity giftEntity = new GiftEntity()
            .setTitle(giftCreateRequest.getTitle())
            .setDescription(giftCreateRequest.getDescription())
            .setUrl(giftCreateRequest.getUrl())
            .setPrice(giftCreateRequest.getPrice())
            .setReserved(giftCreateRequest.isReserved())
            .setStatus(Objects.requireNonNullElse(giftCreateRequest.getStatus(), GiftStatus.OPEN))
            .setVisibility(Objects.requireNonNullElse(giftCreateRequest.getVisibility(), GiftVisibility.PUBLIC))
            .setWishlist(wishlist)
            .setAllowedUsers(new HashSet<>(userService.getByEmails(giftCreateRequest.getAllowedUserEmails())))
            .setPhotoId(photoUrl);

        return giftMapper.toGift(giftRepository.save(giftEntity));
    }

    @Transactional
    public Gift updateGift(Long giftId, GiftUpdateRequest giftUpdateRequest, FileDto file) throws Exception {
        GiftEntity giftEntity = getGiftById(giftId);

        checkOwner(giftEntity);

        if (giftUpdateRequest.getTitle() != null) {
            giftEntity.setTitle(giftUpdateRequest.getTitle().trim());
        }

        if (giftUpdateRequest.getDescription() != null) {
            giftEntity.setDescription(giftUpdateRequest.getDescription().trim());
        }

        if (giftUpdateRequest.getUrl() != null) {
            giftEntity.setUrl(giftUpdateRequest.getUrl());
        }

        if (giftUpdateRequest.getPrice() != null) {
            giftEntity.setPrice(giftUpdateRequest.getPrice());
        }

        if (giftUpdateRequest.getStatus() != null) {
            giftEntity.setStatus(giftUpdateRequest.getStatus());
        }

        if (giftUpdateRequest.getReserved() != null) {
            giftEntity.setReserved(giftUpdateRequest.getReserved());
        }

        if (giftUpdateRequest.getVisibility() != null) {
            giftEntity.setVisibility(giftUpdateRequest.getVisibility());
        }

        if (file != null && file.getFileContent().length > 0) {
            String newPhotoUrl = giftImageService.uploadGiftImage(file);
            giftEntity.setPhotoId(newPhotoUrl);
        }

        if (giftUpdateRequest.getAllowedUserEmails() != null) {
            giftEntity.setAllowedUsers(
                new HashSet<>(
                    userService.getByEmails(giftUpdateRequest.getAllowedUserEmails())
                )
            );
        }

        GiftEntity updatedGift = giftRepository.save(giftEntity);
        return giftMapper.toGift(updatedGift);
    }

    @Transactional
    public Gift reserveGift(Long giftId) throws Exception {
        GiftEntity giftEntity = getGiftById(giftId);

        var currentUser = userService.getAuthenticatedUser();

        giftEntity.setReserver(currentUser);
        giftEntity.setReserved(true);

        GiftEntity updatedGift = giftRepository.save(giftEntity);
        return giftMapper.toGift(updatedGift);
    }

    @Transactional
    public GiftWithImageResponse getGift(Long giftId) throws Exception {
        GiftEntity giftEntity = getGiftById(giftId);

        Gift gift = giftMapper.toGift(giftEntity);
        GiftWithImageResponse response = new GiftWithImageResponse();
        if (giftEntity.getPhotoId() != null) {
            byte[] imageBytes = giftImageService.getFileAsBytes(giftEntity.getPhotoId());
            response.setImage(imageBytes);
        }
        response.setGift(gift);
        return response;
    }

    @Transactional
    public List<GiftWithImageResponse> getGifts() throws Exception {
        var currentUser = userService.getAuthenticatedUser();

        List<GiftEntity> giftEntities = giftRepository.findAllAccessibleGifts(currentUser.getId());
        List<GiftWithImageResponse> responseList = new ArrayList<>();

        for (GiftEntity giftEntity : giftEntities) {
            Gift gift = giftMapper.toGift(giftEntity);
            GiftWithImageResponse response = new GiftWithImageResponse();
            if (giftEntity.getPhotoId() != null) {
                byte[] imageBytes = giftImageService.getFileAsBytes(giftEntity.getPhotoId());
                response.setImage(imageBytes);
            }
            response.setGift(gift);
            responseList.add(response);
        }
        return responseList;
    }

    public List<GiftEntity> getGiftsByIds(List<Long> giftIds) {
        var currentUser = userService.getAuthenticatedUser();

        return giftRepository.findAccessibleGiftsByIds(giftIds, currentUser.getId());
    }

    public GiftEntity getGiftById(Long giftId) throws Exception {
        var currentUser = userService.getAuthenticatedUser();

        return giftRepository.findAccessibleGiftsByIds(List.of(giftId), currentUser.getId()).stream().findFirst()
            .orElseThrow(() -> new NotFoundException("Cannot find or forbidden access to gift with ID: " + giftId));
    }

    private void checkOwner(GiftEntity giftEntity) {
        var currentUser = userService.getAuthenticatedUser();

        WishlistEntity wishlist = giftEntity.getWishlist();

        if (!Objects.equals(wishlist.getOwner().getId(), currentUser.getId())) {
            throw new AccessDeniedRuntimeException(currentUser.getEmail() + " is not the owner of wishlist " + wishlist.getId());
        }
    }

    public WishlistEntity getWishlistById(Long wishlistId) {
        var currentUser = userService.getAuthenticatedUser();

        return wishlistRepository.findAccessibleWishlistsByIds(List.of(wishlistId), currentUser.getId()).stream().findFirst()
            .orElseThrow(() -> new NotFoundException("Cannot find or forbidden access to wishlist with ID: " + wishlistId));
    }

    @Transactional
    public void deleteGift(Long giftId) throws Exception {
        GiftEntity giftEntity = getGiftById(giftId);

        checkOwner(giftEntity);

        String photoUrl = giftEntity.getPhotoId();
        if (photoUrl != null) {
            try {
                giftImageService.deleteGiftImage(photoUrl);
            } catch (Exception e) {
                log.error("Failed to delete image from MinIO: {}", e.getMessage());
            }
        }
        giftRepository.deleteById(giftId);
    }
}
