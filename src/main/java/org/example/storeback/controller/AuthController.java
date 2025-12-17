package org.example.storeback.controller;

import org.example.storeback.controller.webmodel.request.LoginRequest;
import org.example.storeback.controller.webmodel.response.ClientResponse;
import org.example.storeback.controller.webmodel.response.LoginResponse;
import org.example.storeback.domain.models.Role;
import org.example.storeback.domain.service.AuthService;
import org.example.storeback.domain.service.ClientService;
import org.example.storeback.domain.service.dto.ClientDto;
import org.example.storeback.domain.validation.RequiresRole;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final ClientService clientService;
    private final AuthService authService;

    public AuthController(ClientService clientService, AuthService authService) {
        this.clientService = clientService;
        this.authService = authService;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Optional<ClientDto> clientOptional = clientService.login(
                loginRequest.email(),
                loginRequest.password()
        );

        if (clientOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        ClientDto client = clientOptional.get();


        String token = authService.createTokenFromUser(client);

        LoginResponse response = new LoginResponse(
                token,
                client.email(),
                client.name(),
                client.role()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader) {
        String token = extractTokenFromHeader(authHeader);

        if (token == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        authService.deleteToken(token);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<ClientResponse> getAdminUser(@RequestHeader("Authorization") String authHeader) {
        String token = extractTokenFromHeader(authHeader);

        if (token == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Optional<ClientDto> userOptional = authService.getUserFromToken(token);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        ClientDto user = userOptional.get();

        ClientResponse response = new ClientResponse(
                user.id(),
                user.name(),
                user.phone(),
                user.email(),
                null,
                user.cartId(),
                user.role()
        );

        return ResponseEntity.ok(response);
    }
    @GetMapping("/me/any")
    public ResponseEntity<ClientResponse> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        String token = extractTokenFromHeader(authHeader);

        if (token == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Optional<ClientDto> userOptional = authService.getAnyUserFromToken(token);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        ClientDto user = userOptional.get();

        ClientResponse response = new ClientResponse(
                user.id(),
                user.name(),
                user.phone(),
                user.email(),
                null,
                user.cartId(),
                user.role()
        );

        return ResponseEntity.ok(response);
    }



    private String extractTokenFromHeader(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
