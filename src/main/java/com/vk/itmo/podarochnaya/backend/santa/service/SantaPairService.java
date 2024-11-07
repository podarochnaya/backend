package com.vk.itmo.podarochnaya.backend.santa.service;

import com.vk.itmo.podarochnaya.backend.santa.dto.SantaPair;
import com.vk.itmo.podarochnaya.backend.santa.jpa.SantaPairRepository;
import com.vk.itmo.podarochnaya.backend.santa.mapper.SantaGroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SantaPairService {
    private SantaPairRepository pairRepository;
    public Optional<SantaPair> getById(long id) {
        return pairRepository.findById(id).map(SantaGroupMapper::map);
    }

    public List<SantaPair> getAll() {
        return pairRepository.findAll().stream()
                .map(SantaGroupMapper::map)
                .toList();
    }
}
