package com.example.MedicCenter.domain.ports.out;

import com.example.MedicCenter.domain.model.AccessToken;
import com.example.MedicCenter.domain.model.User;

public interface TokenServicePort {
    AccessToken generateToken(User user);

    String extractUsername(String token);

    boolean validateToken(String token, String username);
}
