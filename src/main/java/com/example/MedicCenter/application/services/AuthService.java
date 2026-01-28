package com.example.MedicCenter.application.services;

import com.example.MedicCenter.domain.model.AccessToken;
import com.example.MedicCenter.domain.model.User;
import com.example.MedicCenter.domain.ports.in.AuthUseCase;
import com.example.MedicCenter.domain.ports.out.TokenServicePort;
import com.example.MedicCenter.domain.ports.out.PasswordEncoderPort;
import com.example.MedicCenter.domain.ports.out.UserRepositoryPort;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final TokenServicePort tokenServicePort;
    private final MeterRegistry meterRegistry;

    @Override
    public AccessToken login(String username, String password) {
        User user = userRepositoryPort.findByUsername(username)
                .orElseThrow(() -> {
                    if (meterRegistry != null) {
                        meterRegistry.counter("auth.login.failure", "reason", "user_not_found").increment();
                    }
                    return new RuntimeException("User not found");
                });

        if (passwordEncoderPort.matches(password, user.getPassword())) {
            if (meterRegistry != null) {
                meterRegistry.counter("auth.login.success").increment();
            }
            return tokenServicePort.generateToken(user);
        } else {
            if (meterRegistry != null) {
                meterRegistry.counter("auth.login.failure", "reason", "invalid_password").increment();
            }
            throw new RuntimeException("Invalid credentials");
        }
    }

    @Override
    public User register(User user) {
        user.setPassword(passwordEncoderPort.encode(user.getPassword()));
        return userRepositoryPort.save(user);
    }
}
