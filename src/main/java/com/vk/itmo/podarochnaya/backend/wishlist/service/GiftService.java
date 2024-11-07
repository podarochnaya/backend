package com.vk.itmo.podarochnaya.backend.wishlist.service;

import com.vk.itmo.podarochnaya.backend.exception.NotFoundException;
import com.vk.itmo.podarochnaya.backend.user.jpa.UserEntity;
import com.vk.itmo.podarochnaya.backend.user.service.UserService;
import com.vk.itmo.podarochnaya.backend.wishlist.dto.Gift;
import com.vk.itmo.podarochnaya.backend.wishlist.dto.GiftCreateRequest;
import com.vk.itmo.podarochnaya.backend.wishlist.dto.GiftUpdateRequest;
import com.vk.itmo.podarochnaya.backend.wishlist.dto.GiftWithImageResponse;
import com.vk.itmo.podarochnaya.backend.wishlist.jpa.GiftEntity;
import com.vk.itmo.podarochnaya.backend.wishlist.jpa.GiftRepository;
import com.vk.itmo.podarochnaya.backend.wishlist.jpa.WishlistEntity;
import com.vk.itmo.podarochnaya.backend.wishlist.mapper.GiftMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class GiftService {

    private final GiftRepository giftRepository;
    private final WishlistService wishlistService;
    private final UserService userService;
    private final GiftImageService giftImageService;
    private final GiftMapper giftMapper;

    @Transactional
    public Gift createGift(GiftCreateRequest giftCreateRequest, MultipartFile image) throws Exception {
        WishlistEntity wishlist = wishlistService.getWishlistById(giftCreateRequest.getWishlistId());

        UserEntity user = userService.getById(giftCreateRequest.getReserverUserId());

        String photoUrl = null;
        if (image != null && !image.isEmpty()) {
            photoUrl = giftImageService.uploadGiftImage(image);
        }

        GiftEntity giftEntity = new GiftEntity()
            .setTitle(giftCreateRequest.getTitle())
            .setDescription(giftCreateRequest.getDescription())
            .setUrl(giftCreateRequest.getUrl())
            .setPrice(giftCreateRequest.getPrice())
            .setReserved(giftCreateRequest.isReserved())
            .setStatus(giftCreateRequest.getStatus())
            .setWishlist(wishlist)
            .setReserver(user)
            .setPhotoId(photoUrl);

        return giftMapper.toGift(giftRepository.save(giftEntity));
    }

    @Transactional
    public Gift updateGift(Long giftId, GiftUpdateRequest giftUpdateRequest, MultipartFile file) throws Exception {
        GiftEntity giftEntity = getGiftById(giftId);

        if (giftUpdateRequest.getReserverUserId() != null) {
            UserEntity user = userService.getById(giftUpdateRequest.getReserverUserId());

            giftEntity.setReserver(user);
        }

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

        if (file != null && !file.isEmpty()) {
            String newPhotoUrl = giftImageService.uploadGiftImage(file);
            giftEntity.setPhotoId(newPhotoUrl);
        }

        GiftEntity updatedGift = giftRepository.save(giftEntity);
        return giftMapper.toGift(updatedGift);
    }


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


    public List<GiftWithImageResponse> getGifts() throws Exception {
        List<GiftEntity> giftEntities = giftRepository.findAll();
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

    public GiftEntity getGiftById(Long giftId) throws Exception {
        return giftRepository.findById(giftId)
            .orElseThrow(() -> new NotFoundException("Gift not found. Id: " + giftId));
    }

    @Transactional
    public void deleteGift(Long giftId) throws Exception {
        GiftEntity giftEntity = getGiftById(giftId);

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
