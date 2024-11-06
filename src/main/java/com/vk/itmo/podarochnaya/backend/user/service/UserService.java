package com.vk.itmo.podarochnaya.backend.user.service;

import com.vk.itmo.podarochnaya.backend.exception.DataConflictException;
import com.vk.itmo.podarochnaya.backend.exception.NotFoundException;
import com.vk.itmo.podarochnaya.backend.user.jpa.UserEntity;
import com.vk.itmo.podarochnaya.backend.user.jpa.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserRepository repository;

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public UserEntity save(UserEntity UserEntity) {
        return repository.save(UserEntity);
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
}
