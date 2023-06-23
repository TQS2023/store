package pt.ua.deti.store.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import pt.ua.deti.store.database.PickupPointEntity;
import pt.ua.deti.store.database.UserEntity;
import pt.ua.deti.store.database.UserRepository;
import pt.ua.deti.store.entities.*;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.emptyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:inmemdb.properties")
@SpringBootTest
class AuthenticationServiceTest {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("Test if login functions correctly")
    void testLogin() {
        when(userRepository.findByEmail("user")).thenReturn(new UserEntity(
                passwordEncoder.encode("password"),
                "address",
                "user",
                "creditCardNumber",
                123L,
                "creditCardCVC",
                new PickupPointEntity(
                        UUID.randomUUID(),
                        "name",
                        "address"
                )
        ));

        LoginRequest request = new LoginRequest("user", "password");
        TokenResponse response = authenticationService.login(request);

        assertThat(response, is(notNullValue()));
        assertThat(response.getToken(), is(notNullValue()));
        assertThat(response.isSuccess(), is(true));

        verify(userRepository, times(1)).findByEmail("user");
    }

    @Test
    @DisplayName("Test if login fails when user does not exist")
    void testLoginFail() {
        when(userRepository.findByEmail("user")).thenReturn(null);

        LoginRequest request = new LoginRequest("user", "password");
        TokenResponse response = authenticationService.login(request);

        assertThat(response, is(notNullValue()));
        assertThat(response.getToken(), is(emptyString()));
        assertThat(response.isSuccess(), is(false));

        verify(userRepository, times(1)).findByEmail("user");
    }

    @Test
    @DisplayName("Test if login fails when password is incorrect")
    void testLoginFailIncorrectPassword() {
        when(userRepository.findByEmail("user")).thenReturn(new UserEntity(
                passwordEncoder.encode("password"),
                "address",
                "user",
                "creditCardNumber",
                123L,
                "creditCardCVC",
                new PickupPointEntity(
                        UUID.randomUUID(),
                        "name",
                        "address"
                )
        ));

        LoginRequest request = new LoginRequest("user", "incorrect");
        TokenResponse response = authenticationService.login(request);

        assertThat(response, is(notNullValue()));
        assertThat(response.getToken(), is(emptyString()));
        assertThat(response.isSuccess(), is(false));

        verify(userRepository, times(1)).findByEmail("user");
    }

    @Test
    @DisplayName("Test if login fails when any field is null")
    void testLoginFailNull() {
        when(userRepository.findByEmail("user")).thenReturn(new UserEntity(
                passwordEncoder.encode("password"),
                "address",
                "user",
                "creditCardNumber",
                123L,
                "creditCardCVC",
                new PickupPointEntity(
                        UUID.randomUUID(),
                        "name",
                        "address"
                )
        ));

        LoginRequest request = new LoginRequest(null, "password");
        TokenResponse response = authenticationService.login(request);

        assertThat(response, is(notNullValue()));
        assertThat(response.getToken(), is(emptyString()));
        assertThat(response.isSuccess(), is(false));

        verify(userRepository, times(0)).findByEmail(anyString());
    }

    @Test
    @DisplayName("Test if register works normally")
    void testRegister() {
        when(userRepository.existsByEmail("user")).thenReturn(false);

        UserRequest request = new UserRequest(
                "password",
                "address",
                "user",
                "creditCardNumber",
                123L,
                "creditCardCVC",
                UUID.randomUUID().toString()
        );
        TokenResponse response = authenticationService.register(request);

        assertThat(response, is(notNullValue()));
        assertThat(response.getToken(), is(notNullValue()));
        assertThat(response.isSuccess(), is(true));

        verify(userRepository, times(1)).findByEmail("user");
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("Test if register fails when user already exists")
    void testRegisterFail() {
        when(userRepository.existsByEmail("user")).thenReturn(true);

        UserRequest request = new UserRequest(
                "password",
                "address",
                "user",
                "creditCardNumber",
                123L,
                "creditCardCVC",
                UUID.randomUUID().toString()
        );
        TokenResponse response = authenticationService.register(request);

        assertThat(response, is(notNullValue()));
        assertThat(response.getToken(), is(emptyString()));
        assertThat(response.isSuccess(), is(false));

        verify(userRepository, times(1)).existsByEmail("user");
        verify(userRepository, times(0)).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("Test if register fails when preferredPickupPointId is not a valid UUID")
    void testRegisterFailInvalidPreferredPickupPointId() {
        when(userRepository.existsByEmail("user")).thenReturn(false);

        UserRequest request = new UserRequest(
                "password",
                "address",
                "user",
                "creditCardNumber",
                123L,
                "creditCardCVC",
                "invalid"
        );
        TokenResponse response = authenticationService.register(request);

        assertThat(response, is(notNullValue()));
        assertThat(response.getToken(), is(emptyString()));
        assertThat(response.isSuccess(), is(false));

        verify(userRepository, times(0)).existsByEmail("user");
        verify(userRepository, times(0)).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("Test if register fails when any field is null")
    void testRegisterFailNull() {
        when(userRepository.existsByEmail("user")).thenReturn(false);

        UserRequest request = new UserRequest(
                "password",
                "address",
                "user",
                "creditCardNumber",
                123L,
                "creditCardCVC",
                null
        );
        TokenResponse response = authenticationService.register(request);

        assertThat(response, is(notNullValue()));
        assertThat(response.getToken(), is(emptyString()));
        assertThat(response.isSuccess(), is(false));

        verify(userRepository, times(0)).existsByEmail("user");
        verify(userRepository, times(0)).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("Test if getting the profile works")
    void testGetProfile() {
        when(userRepository.findByEmail("user")).thenReturn(new UserEntity(
                passwordEncoder.encode("password"),
                "address",
                "user",
                "creditCardNumber",
                123L,
                "creditCardCVC",
                new PickupPointEntity(
                        UUID.randomUUID(),
                        "name",
                        "address"
                )
        ));

        ProfileResponse response = authenticationService.profile("user");

        assertThat(response, is(notNullValue()));

        verify(userRepository, times(1)).findByEmail("user");
    }

    @Test
    @DisplayName("Test if updating the profile works")
    void testUpdateProfile() {
        when(userRepository.findByEmail("user")).thenReturn(new UserEntity(
                passwordEncoder.encode("password"),
                "address",
                "user",
                "creditCardNumber",
                123L,
                "creditCardCVC",
                new PickupPointEntity(
                        UUID.randomUUID(),
                        "name",
                        "address"
                )
        ));

        UserRequest request = new UserRequest(
                "password",
                "address",
                "user",
                null,
                null,
                null,
                null
        );
        ProfileUpdateResponse response = authenticationService.updateProfile("user", request);

        assertThat(response, is(notNullValue()));
        assertThat(response.isSuccess(), is(true));

        verify(userRepository, times(1)).findByEmail("user");
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }
}
