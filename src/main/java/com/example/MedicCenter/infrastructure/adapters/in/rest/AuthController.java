package com.example.MedicCenter.infrastructure.adapters.in.rest;

import com.example.MedicCenter.domain.model.AccessToken;
import com.example.MedicCenter.domain.model.User;
import com.example.MedicCenter.domain.ports.in.AuthUseCase;
import com.example.MedicCenter.infrastructure.adapters.in.rest.dtos.AuthRequest;
import com.example.MedicCenter.infrastructure.adapters.in.rest.dtos.AuthResponse;
import com.example.MedicCenter.infrastructure.adapters.in.rest.dtos.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user registration and login")
public class AuthController {

    private final AuthUseCase authUseCase;

    @Operation(summary = "Register a new user", description = "Creates a new user account with a specific role")
    @ApiResponse(responseCode = "200", description = "User registered successfully")
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = @ExampleObject(value = "{\n  \"username\": \"admin\",\n  \"password\": \"admin123\",\n  \"role\": \"ROLE_ADMIN\"\n}"))) @RequestBody RegisterRequest request) {
        User user = new User(null, request.getUsername(), request.getPassword(), request.getRole());
        authUseCase.register(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @Operation(summary = "Login to get access token", description = "Returns a JWT token for authentication")
    @ApiResponse(responseCode = "200", description = "Successfully authenticated", content = @Content(schema = @Schema(implementation = AuthResponse.class)))
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = @ExampleObject(value = "{\n  \"username\": \"admin\",\n  \"password\": \"admin123\"\n}"))) @RequestBody AuthRequest request) {
        AccessToken accessToken = authUseCase.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(AuthResponse.builder()
                .token(accessToken.getToken())
                .type(accessToken.getType())
                .expiresIn(accessToken.getExpiresIn())
                .build());
    }
}
