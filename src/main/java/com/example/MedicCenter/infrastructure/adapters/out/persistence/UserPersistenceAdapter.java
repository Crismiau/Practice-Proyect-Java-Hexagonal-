package com.example.MedicCenter.infrastructure.adapters.out.persistence;

import com.example.MedicCenter.domain.model.User;
import com.example.MedicCenter.domain.ports.out.UserRepositoryPort;
import com.example.MedicCenter.infrastructure.adapters.out.persistence.entities.UserEntity;
import com.example.MedicCenter.infrastructure.adapters.out.persistence.repositories.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRepositoryPort {

    private final JpaUserRepository repository;

    @Override
    public User save(User user) {
        UserEntity entity = UserEntity.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
        UserEntity saved = repository.save(entity);
        return new User(saved.getId(), saved.getUsername(), saved.getPassword(), saved.getRole());
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username)
                .map(e -> new User(e.getId(), e.getUsername(), e.getPassword(), e.getRole()));
    }
}
