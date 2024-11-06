package com.vk.itmo.podarochnaya.backend.santa.controller;

import com.vk.itmo.podarochnaya.backend.santa.dto.SantaGroup;
import com.vk.itmo.podarochnaya.backend.santa.dto.SantaGroupCreateRequest;
import com.vk.itmo.podarochnaya.backend.santa.dto.SantaGroupUpdateRequest;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v1/santa-groups")
public class SantaGroupController {

    @GetMapping("/{id}")
    public SantaGroup getSantaGroupById(
        @PathVariable Long id
    ) {
        throw new UnsupportedOperationException("getSantaGroupById is not implemented yet");
    }

    @GetMapping
    public List<SantaGroup> getAllSantaGroups() {
        throw new UnsupportedOperationException("getAllSantaGroups is not implemented yet");
    }

    @PostMapping
    public SantaGroup createSantaGroup(
        @Valid @RequestBody SantaGroupCreateRequest santaGroup
    ) {
        throw new UnsupportedOperationException("createSantaGroup is not implemented yet");
    }

    @PutMapping("/{id}")
    public SantaGroup updateSantaGroup(
        @PathVariable Long id,
        @Valid @RequestBody SantaGroupUpdateRequest santaGroup
    ) {
        throw new UnsupportedOperationException("updateSantaGroup is not implemented yet");
    }

    @DeleteMapping("/{id}")
    public void deleteSantaGroup(
        @PathVariable Long id
    ) {
        throw new UnsupportedOperationException("deleteSantaGroup is not implemented yet");
    }
}
