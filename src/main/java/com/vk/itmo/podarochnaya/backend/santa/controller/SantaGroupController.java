package com.vk.itmo.podarochnaya.backend.santa.controller;

import com.vk.itmo.podarochnaya.backend.santa.dto.SantaGroup;
import com.vk.itmo.podarochnaya.backend.santa.dto.SantaGroupCreateRequest;
import com.vk.itmo.podarochnaya.backend.santa.dto.SantaGroupUpdateRequest;
import com.vk.itmo.podarochnaya.backend.santa.service.SantaGroupService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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

import static com.vk.itmo.podarochnaya.backend.santa.mapper.SantaGroupMapper.map;

@RestController
@RequestMapping("/api/v1/santa-groups")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SantaGroupController {
    private final SantaGroupService santaGroupService;

    @GetMapping("/{id}")
    public ResponseEntity<SantaGroup> getSantaGroupById(@PathVariable Long id) {
        return santaGroupService.getById(id).isPresent()
                ? ResponseEntity.ok(santaGroupService.getById(id).get())
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<SantaGroup>> getAllSantaGroups() {
        return ResponseEntity.ok(santaGroupService.getAll());
    }

    @PostMapping
    public ResponseEntity<SantaGroup> createSantaGroup(
            @Valid @RequestBody SantaGroupCreateRequest santaGroup
    ) {
        return ResponseEntity.ok(map(santaGroupService.create(santaGroup)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SantaGroup> updateSantaGroup(
            @PathVariable Long id,
            @Valid @RequestBody SantaGroupUpdateRequest santaGroup
    ) {
        return ResponseEntity.ok(map(santaGroupService.update(id, santaGroup)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSantaGroup(@PathVariable Long id) {
        santaGroupService.delete(id);
        return ResponseEntity.ok().build();
    }
}
