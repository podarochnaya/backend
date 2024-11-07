package com.vk.itmo.podarochnaya.backend.santa.service;

import com.vk.itmo.podarochnaya.backend.exception.NotFoundException;
import com.vk.itmo.podarochnaya.backend.santa.dto.SantaPair;
import com.vk.itmo.podarochnaya.backend.santa.dto.SantaPairCreateRequest;
import com.vk.itmo.podarochnaya.backend.santa.dto.SantaPairUpdateRequest;
import com.vk.itmo.podarochnaya.backend.santa.jpa.SantaGroupRepository;
import com.vk.itmo.podarochnaya.backend.santa.jpa.SantaPairEntity;
import com.vk.itmo.podarochnaya.backend.santa.jpa.SantaPairRepository;
import com.vk.itmo.podarochnaya.backend.santa.mapper.SantaGroupMapper;
import com.vk.itmo.podarochnaya.backend.user.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.vk.itmo.podarochnaya.backend.santa.mapper.SantaGroupMapper.map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SantaPairService {
    private final SantaPairRepository pairRepository;
    private final UserRepository userRepository;
    private final SantaGroupRepository santaGroupRepository;

    public Optional<SantaPair> getById(long id) {
        return pairRepository.findById(id).map(SantaGroupMapper::map);
    }

    public List<SantaPair> getAll() {
        return pairRepository.findAll().stream()
                .map(SantaGroupMapper::map)
                .toList();
    }

    public SantaPair create(SantaPairCreateRequest request) {
        var taker = userRepository.findById(request.getTakerUserId())
                .orElseThrow(() -> new NotFoundException("Cannot find user by id: " + request.getTakerUserId()));
        var giver = userRepository.findById(request.getGiverUserId())
                .orElseThrow(() -> new NotFoundException("Cannot find user by id: " + request.getGiverUserId()));
        var group = santaGroupRepository.findById(request.getSantaGroupId())
                .orElseThrow(() -> new NotFoundException("Cannot find santa group by id: " + request.getSantaGroupId()));

        return map(pairRepository.save(SantaPairEntity.builder()
                .taker(taker)
                .giver(giver)
                .deleted(false)
                .santaGroup(group)
                .build())
        );
    }

    public SantaPair update(long id, SantaPairUpdateRequest request) {
        var pair = pairRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot find pair by id: " + id));
        pair.setDeleted(request.getDeleted());
        return map(pairRepository.save(pair));
    }

    public void delete(long id) {
        pairRepository.deleteById(id);
    }

    public List<SantaPair> distribute(long id) {
        var group = santaGroupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot find santa group by id: " + id));
        var users = new ArrayList<>(group.getUsers());
        Collections.shuffle(users);
        List<SantaPairEntity> pairs = new ArrayList<>(users.size());
        for (int i = 0; i < users.size(); i++) {
            pairs.add(SantaPairEntity.builder()
                    .santaGroup(group)
                    .giver(users.get(i))
                    .taker(users.get(i == users.size() - 1 ? 0 : i + 1))
                    .deleted(false)
                    .build());
        }
        return pairRepository.saveAll(pairs).stream().map(SantaGroupMapper::map).toList();
    }
}
