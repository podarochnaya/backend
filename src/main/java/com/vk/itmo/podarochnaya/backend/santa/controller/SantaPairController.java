package com.vk.itmo.podarochnaya.backend.santa.controller;

import com.vk.itmo.podarochnaya.backend.santa.dto.SantaPair;
import com.vk.itmo.podarochnaya.backend.santa.dto.SantaPairRequest;
import com.vk.itmo.podarochnaya.backend.santa.service.SantaPairService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/santa-pairs")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SantaPairController {
    private final SantaPairService santaPairService;

    @GetMapping("/{id}")
    public ResponseEntity<SantaPair> getSantaPairById(@PathVariable Long id) {
        return santaPairService.getById(id).isPresent()
                ? ResponseEntity.ok(santaPairService.getById(id).get())
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<SantaPair>> getAllSantaPairs() {
        return ResponseEntity.ok(santaPairService.getAll());
    }

    @PostMapping
    public ResponseEntity<SantaPair> createSantaPair(@RequestBody SantaPairRequest santaPair) {
        throw new UnsupportedOperationException("createSantaPair is not implemented yet");
    }

    @PutMapping("/{id}")
    public ResponseEntity<SantaPair> updateSantaPair(
            @PathVariable Long id,
            @RequestBody SantaPairRequest santaPair
    ) {
        throw new UnsupportedOperationException("updateSantaPair is not implemented yet");
    }

    @PostMapping("distribute-free/{id}")
    public ResponseEntity<SantaPair> distributeFreeUsersToSantaPairs(@PathVariable Long id) {
        throw new UnsupportedOperationException("distributeFreeUsersToSantaPairs is not implemented yet");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSantaPair(
            @PathVariable Long id
    ) {
        throw new UnsupportedOperationException("deleteSantaPair is not implemented yet");
    }
}
