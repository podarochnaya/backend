package com.vk.itmo.podarochnaya.backend.santa.service;

import com.vk.itmo.podarochnaya.backend.exception.NotFoundException;
import com.vk.itmo.podarochnaya.backend.santa.dto.SantaGroup;
import com.vk.itmo.podarochnaya.backend.santa.dto.SantaGroupCreateRequest;
import com.vk.itmo.podarochnaya.backend.santa.dto.SantaGroupUpdateRequest;
import com.vk.itmo.podarochnaya.backend.santa.jpa.SantaGroupEntity;
import com.vk.itmo.podarochnaya.backend.santa.jpa.SantaGroupRepository;
import com.vk.itmo.podarochnaya.backend.santa.jpa.SantaPairRepository;
import com.vk.itmo.podarochnaya.backend.santa.jpa.UserSantaGroupsRepository;
import com.vk.itmo.podarochnaya.backend.santa.jpa.UsersSantaGroupEntity;
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
    private final UserSantaGroupsRepository userSantaGroupsRepository;

    public Optional<SantaGroup> getById(long id) {
        return groupRepository.findById(id).map(SantaGroupMapper::map);
    }

    public List<SantaGroup> getAll() {
        return groupRepository.findAll().stream().map(SantaGroupMapper::map).toList();
    }

    public SantaGroupEntity create(SantaGroupCreateRequest request) {
        var owner = userRepository.findById(request.getOwnerUserId()).orElseThrow();
        var pairs = pairRepository.findByIdIn(request.getSantaPairIds());
        var users = userRepository.findByIdIn(request.getUserIds());
        var savedGroup = groupRepository.save(SantaGroupEntity.builder()
                .owner(owner)
                .santaPairs(pairs)
                .title(request.getTitle())
                .expiration(request.getExpiration())
                .status(request.getStatus().getStatus())
                .users(new HashSet<>(users))
                .build());
        userSantaGroupsRepository.saveAll(users.stream()
                .map(user -> UsersSantaGroupEntity.builder()
                        .santaGroupId(savedGroup.getId())
                        .userId(user.getId())
                        .build())
                .toList()
        );
        return savedGroup;
    }

    public SantaGroupEntity update(Long id, SantaGroupUpdateRequest request) {
        Optional<SantaGroupEntity> groupEntityOptional = groupRepository.findById(id);
        if (groupEntityOptional.isEmpty()) {
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
        request.getStatusOpt().ifPresent(status -> entityBuilder.status(request.getStatus().getStatus()));
        var entity = entityBuilder.build();
        entity.setId(id);
        return groupRepository.save(entityBuilder.build());
    }

    public void delete(Long id) {
        groupRepository.deleteById(id);
    }
}
