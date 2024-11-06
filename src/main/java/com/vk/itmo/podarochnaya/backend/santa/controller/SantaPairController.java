package com.vk.itmo.podarochnaya.backend.santa.controller;

import com.vk.itmo.podarochnaya.backend.santa.dto.SantaPair;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/santa-pairs")
public class SantaPairController {

    @GetMapping("/{id}")
    public SantaPair getSantaPairById(
        @PathVariable Long id
    ) {
        throw new UnsupportedOperationException("getSantaPairById is not implemented yet");
    }

    @GetMapping
    public List<SantaPair> getAllSantaPairs() {
        throw new UnsupportedOperationException("getAllSantaPairs is not implemented yet");
    }

    @PostMapping
    public SantaPair createSantaPair(
        @RequestBody SantaPair santaPair
    ) {
        throw new UnsupportedOperationException("createSantaPair is not implemented yet");
    }

    @PutMapping("/{id}")
    public SantaPair updateSantaPair(
        @PathVariable Long id,
        @RequestBody SantaPair santaPair
    ) {
        throw new UnsupportedOperationException("updateSantaPair is not implemented yet");
    }

    @DeleteMapping("/{id}")
    public void deleteSantaPair(
        @PathVariable Long id
    ) {
        throw new UnsupportedOperationException("deleteSantaPair is not implemented yet");
    }
}
