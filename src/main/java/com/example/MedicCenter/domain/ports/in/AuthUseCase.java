package com.example.MedicCenter.domain.ports.in;

import com.example.MedicCenter.domain.model.AccessToken;
import com.example.MedicCenter.domain.model.User;

public interface AuthUseCase {
    AccessToken login(String username, String password);

    User register(User user);
}
