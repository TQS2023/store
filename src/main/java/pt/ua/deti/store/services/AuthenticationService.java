package pt.ua.deti.store.services;

import org.h2.engine.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pt.ua.deti.store.database.PickupPointRepository;
import pt.ua.deti.store.database.UserEntity;
import pt.ua.deti.store.database.UserRepository;
import pt.ua.deti.store.entities.*;
import pt.ua.deti.store.security.Jwt;
import pt.ua.deti.store.security.UserDetailedView;

import java.util.UUID;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PickupPointRepository pickupPointRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PickupPointRepository pickupPointRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.pickupPointRepository = pickupPointRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public TokenResponse login(LoginRequest loginRequest) {
        if (loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
            return new TokenResponse("", false);
        }

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            UserDetailedView user = (UserDetailedView) auth.getPrincipal();
            String token = Jwt.generateToken(user);
            return new TokenResponse(token, true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new TokenResponse("", false);
        }
    }

    public TokenResponse register(UserRequest user) {
        if (
                user.getEmail() == null ||
                user.getPassword() == null ||
                user.getAddress() == null ||
                user.getCreditCardCVC() == null ||
                user.getCreditCardNumber() == null ||
                user.getCreditCardValidity() == null ||
                user.getPreferredPickupPointId() == null ||
                !user.getPreferredPickupPointId().matches("[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}")
        ) {
            return new TokenResponse("", false);
        }

        boolean exists = userRepository.existsByEmail(user.getEmail());
        if (exists) {
            return new TokenResponse("", false);
        }

        boolean existsPickup = pickupPointRepository.existsByPickupPointId(
                UUID.fromString(user.getPreferredPickupPointId())
        );
        if (!existsPickup) {
            return new TokenResponse("", false);
        }

        UserEntity newUser = user.toEntity();
        newUser.setPreferredPickupPointId(
                pickupPointRepository.findByPickupPointId(
                        UUID.fromString(user.getPreferredPickupPointId())
                )
        );
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(newUser);

        return login(new LoginRequest(user.getEmail(), user.getPassword()));
    }

    public ProfileResponse profile(String user) {
        return ProfileResponse.fromEntity(userRepository.findByEmail(user));
    }

    public ProfileUpdateResponse updateProfile(String user, UserRequest userRequest) {
        UserEntity originalUser = userRepository.findByEmail(user);

        if (userRequest.getEmail() != null) {
            originalUser.setEmail(userRequest.getEmail());
        }

        if (userRequest.getPassword() != null) {
            originalUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        if (userRequest.getAddress() != null) {
            originalUser.setAddress(userRequest.getAddress());
        }


        if (userRequest.getCreditCardCVC() != null) {
            originalUser.setCreditCardCVC(userRequest.getCreditCardCVC());
        }

        if (userRequest.getCreditCardNumber() != null) {
            originalUser.setCreditCardNumber(userRequest.getCreditCardNumber());
        }

        if (userRequest.getCreditCardValidity() != null) {
            originalUser.setCreditCardValidity(userRequest.getCreditCardValidity());
        }

        if (userRequest.getPreferredPickupPointId() != null) {
            if (!userRequest.getPreferredPickupPointId().matches("[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}")) {
                return new ProfileUpdateResponse(false);
            }

            if (!pickupPointRepository.existsByPickupPointId(
                    UUID.fromString(userRequest.getPreferredPickupPointId())
            )) {
                return new ProfileUpdateResponse(false);
            }

            originalUser.setPreferredPickupPointId(
                    pickupPointRepository.findByPickupPointId(
                            UUID.fromString(userRequest.getPreferredPickupPointId())
                    )
            );
        }

        userRepository.save(originalUser);
        return new ProfileUpdateResponse(true);
    }
}
