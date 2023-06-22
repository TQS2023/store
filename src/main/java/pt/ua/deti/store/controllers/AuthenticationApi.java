package pt.ua.deti.store.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pt.ua.deti.store.entities.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationApi {
    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest loginRequest) {
        return null;
    }

    @PostMapping("/register")
    public TokenResponse register(@RequestBody UserRequest user) {
        return null;
    }

    @PreAuthorize("@jwtFilter.filter()")
    @GetMapping("/profile")
    public ProfileResponse profile() {
        return null;
    }

    @PreAuthorize("@jwtFilter.filter()")
    @PostMapping("/profile")
    public ProfileUpdateResponse updateProfile(@RequestBody ProfileRequest newProfile) {
        return null;
    }
}
