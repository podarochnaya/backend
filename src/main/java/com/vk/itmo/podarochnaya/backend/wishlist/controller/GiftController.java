package com.vk.itmo.podarochnaya.backend.wishlist.controller;

import com.vk.itmo.podarochnaya.backend.wishlist.dto.Gift;
import com.vk.itmo.podarochnaya.backend.wishlist.dto.GiftCreateRequest;
import com.vk.itmo.podarochnaya.backend.wishlist.dto.GiftUpdateRequest;
import com.vk.itmo.podarochnaya.backend.wishlist.dto.GiftWithImageResponse;
import com.vk.itmo.podarochnaya.backend.wishlist.service.GiftService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gifts")
public class GiftController {

    private final GiftService giftService;

    @PostMapping
    public ResponseEntity<Gift> createGift(
        @RequestPart("file") MultipartFile file,
        @Valid @RequestPart(value = "data", required = false) GiftCreateRequest request) throws Exception {
        Gift createdGift = giftService.createGift(request, file);
        return ResponseEntity.ok(createdGift);
    }

    @GetMapping
    public ResponseEntity<List<GiftWithImageResponse>> getAllGifts() throws Exception {
        return ResponseEntity.ok(giftService.getGifts());
    }

    @GetMapping("/{giftId}")
    public ResponseEntity<GiftWithImageResponse> getGiftById(@PathVariable Long giftId) throws Exception {
        return ResponseEntity.ok(giftService.getGift(giftId));
    }

    @PutMapping("/{giftId}")
    public ResponseEntity<Gift> updateGift(
        @PathVariable Long giftId,
        @Valid @RequestPart("data") GiftUpdateRequest request,
        @RequestPart(value = "file", required = false) MultipartFile file) throws Exception {
        return ResponseEntity.ok(giftService.updateGift(giftId, request, file));
    }

    @DeleteMapping("/{giftId}")
    public ResponseEntity<Void> deleteGift(@PathVariable Long giftId) throws Exception {
        giftService.deleteGift(giftId);
        return ResponseEntity.ok().build();
    }
}
