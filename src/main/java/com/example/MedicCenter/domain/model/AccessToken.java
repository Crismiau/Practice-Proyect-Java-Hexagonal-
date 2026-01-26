package com.example.MedicCenter.domain.model;

import java.util.Date;

public class AccessToken {
    private final String token;
    private final String type;
    private final long expiresIn;

    public AccessToken(String token, String type, long expiresIn) {
        this.token = token;
        this.type = type;
        this.expiresIn = expiresIn;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public long getExpiresIn() {
        return expiresIn;
    }
}
