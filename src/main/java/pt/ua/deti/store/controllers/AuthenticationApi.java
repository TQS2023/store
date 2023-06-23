package pt.ua.deti.store.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pt.ua.deti.store.entities.*;
import pt.ua.deti.store.security.Jwt;
import pt.ua.deti.store.services.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationApi {
    private final AuthenticationService service;

    public AuthenticationApi(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        TokenResponse tokenResponse = service.login(loginRequest);
        if (Boolean.FALSE.equals(tokenResponse.isSuccess())) {
            return ResponseEntity.status(401).body(tokenResponse);
        }

        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody UserRequest user) {
        TokenResponse tokenResponse = service.register(user);
        if (Boolean.FALSE.equals(tokenResponse.isSuccess())) {
            return ResponseEntity.internalServerError().body(tokenResponse);
        }

        return ResponseEntity.ok(tokenResponse);
    }

    @PreAuthorize("@jwtFilter.filter()")
    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> profile(@RequestHeader("Authorization") String token) {
        String user = Jwt.getSubject(token.replace("Bearer ", ""));
        return ResponseEntity.ok(service.profile(user));
    }

    @PreAuthorize("@jwtFilter.filter()")
    @PutMapping("/profile")
    public ResponseEntity<ProfileUpdateResponse> updateProfile(@RequestHeader("Authorization") String token, @RequestBody UserRequest userRequest) {
        String user = Jwt.getSubject(token.replace("Bearer ", ""));
        ProfileUpdateResponse response = service.updateProfile(user, userRequest);
        if (Boolean.FALSE.equals(response.isSuccess())) {
            return ResponseEntity.internalServerError().body(response);
        }
        return ResponseEntity.ok(response);
    }
}
