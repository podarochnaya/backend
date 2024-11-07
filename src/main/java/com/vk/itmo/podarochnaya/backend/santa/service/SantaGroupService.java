package com.vk.itmo.podarochnaya.backend.santa.service;

import com.vk.itmo.podarochnaya.backend.exception.NotFoundException;
import com.vk.itmo.podarochnaya.backend.santa.dto.SantaGroup;
import com.vk.itmo.podarochnaya.backend.santa.dto.SantaGroupRequest;
import com.vk.itmo.podarochnaya.backend.santa.jpa.SantaGroupEntity;
import com.vk.itmo.podarochnaya.backend.santa.jpa.SantaGroupRepository;
import com.vk.itmo.podarochnaya.backend.santa.jpa.SantaPairRepository;
import com.vk.itmo.podarochnaya.backend.santa.mapper.SantaGroupMapper;
import com.vk.itmo.podarochnaya.backend.user.jpa.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SantaGroupService {
    private final SantaGroupRepository groupRepository;
    private final SantaPairRepository pairRepository;
    private final UserRepository userRepository;

    public Optional<SantaGroup> getById(long id) {
        return groupRepository.findById(id).map(SantaGroupMapper::map);
    }

    public List<SantaGroup> getAll() {
        return groupRepository.findAll().stream().map(SantaGroupMapper::map).toList();
    }

    public SantaGroupEntity create(SantaGroupRequest request) {
        var owner = userRepository.findById(request.getOwnerUserId()).orElseThrow();
        var pairs = pairRepository.findByIdIn(request.getSantaPairIds());
        var users = userRepository.findByIdIn(request.getUserIds());
        return groupRepository.save(SantaGroupEntity.builder()
                .owner(owner)
                .santaPairs(pairs)
                .title(request.getTitle())
                .users(new HashSet<>(users))
                .expiration(request.getExpiration())
                .status(Integer.parseInt(request.getStatus()))
                .build());
    }

    public SantaGroupEntity update(Long id, SantaGroupRequest request) {
        Optional<SantaGroupEntity> giftEntityOptional = groupRepository.findById(id);
        if (giftEntityOptional.isEmpty()) {
            throw new NotFoundException("Group not found");
        }
        var entityBuilder = SantaGroupEntity.builder();
        if (!request.getSantaPairIds().isEmpty()) {
            entityBuilder.santaPairs(pairRepository.findByIdIn(request.getSantaPairIds()));
        }
        if (!request.getUserIds().isEmpty()) {
            var users = userRepository.findByIdIn(request.getUserIds());
            entityBuilder.users(new HashSet<>(users));
        }
        request.getOwnerUserIdOpt().ifPresent(ownerId -> {
            var owner = userRepository.findById(ownerId).orElseThrow();
            entityBuilder.owner(owner);
        });
        request.getTitleOpt().ifPresent(entityBuilder::title);
        request.getExpirationOpt().ifPresent(entityBuilder::expiration);
        request.getStatusOpt().ifPresent(status -> entityBuilder.status(Integer.parseInt(request.getStatus())));

        return groupRepository.save(entityBuilder.build());
    }

    public void delete(Long id) {
        groupRepository.deleteById(id);
    }
}
