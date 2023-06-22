package pt.ua.deti.store.services;

import org.springframework.stereotype.Service;
import pt.ua.deti.store.entities.*;

@Service
public class AuthenticationService {
    public TokenResponse login(LoginRequest loginRequest) {
        return null;
    }

    public TokenResponse register(UserRequest user) {
        return null;
    }

    public ProfileResponse profile(String user) {
        return null;
    }

    public ProfileUpdateResponse updateProfile(String user, UserRequest userRequest) {
        return null;
    }
}
