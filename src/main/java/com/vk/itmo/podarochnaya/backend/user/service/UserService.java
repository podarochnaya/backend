package com.vk.itmo.podarochnaya.backend.user.service;

import com.vk.itmo.podarochnaya.backend.exception.DataConflictException;
import com.vk.itmo.podarochnaya.backend.exception.NotFoundException;
import com.vk.itmo.podarochnaya.backend.user.dto.UserResponse;
import com.vk.itmo.podarochnaya.backend.user.dto.UserUpdateRequest;
import com.vk.itmo.podarochnaya.backend.user.jpa.UserEntity;
import com.vk.itmo.podarochnaya.backend.user.jpa.UserRepository;
import com.vk.itmo.podarochnaya.backend.user.mapper.UserMapper;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final UserMapper mapper;

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public UserEntity save(UserEntity userEntity) {
        return repository.save(userEntity);
    }

    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    public UserEntity create(UserEntity user) {
        if (repository.existsByEmail(user.getEmail())) {
            throw new DataConflictException("Пользователь с таким email уже существует");
        }

        return save(user);
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    public UserEntity getByUsername(String email) {
        return repository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException("User not found with email: " + email));
    }

    public UserEntity getById(long id) {
        return repository.findById(id)
            .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    public List<UserEntity> getByIds(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }

        return repository.findAllById(ids);
    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }


    public Long deleteById(Long userId) {
        UserEntity authenticatedUser = getAuthenticatedUser();
        UserEntity userEntity =  repository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        if (!authenticatedUser.getEmail().equals(userEntity.getEmail())) {
            throw new AccessDeniedException("Вы не имеете права удалять этот аккаунт");
        }
        repository.deleteById(userId);
        return userId;
    }

    public List<UserResponse> getAllUsers() {
        return mapper.toUserResponseList(repository.findAll());
    }

    public UserResponse updateUserById(Long userId, UserUpdateRequest userRequest) {
        UserEntity authenticatedUser = getAuthenticatedUser();
        UserEntity userEntity =  repository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        if (!authenticatedUser.getEmail().equals(userEntity.getEmail())) {
            throw new AccessDeniedException("Вы не имеете права изменять этот аккаунт");
        }

        if (userRequest.getEmail() != null && !userRequest.getEmail().equals(userEntity.getEmail())) {
            boolean emailExists = repository.existsByEmailAndIdNot(userRequest.getEmail(), userId);
            if (emailExists) {
                throw new DataConflictException("Такой email уже существует");
            }
            userEntity.setEmail(userRequest.getEmail());
        }
        if (userRequest.getFullName() != null) {
            userEntity.setFullname(userRequest.getFullName());
        }
        if (userRequest.getPassword() != null) {
            userEntity.setPasswordHash(passwordEncoder.encode(userRequest.getPassword()));
        }
        if (userRequest.getBirthday() != null) {
            userEntity.setBirthday(userRequest.getBirthday());
        }
        UserEntity updatedUser = repository.save(userEntity);
        return mapper.toUserResponse(updatedUser);
    }

    private UserEntity getAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return (UserEntity) principal;
        } else {
            throw new AccessDeniedException("Не удалось определить пользователя");
        }
    }
}
