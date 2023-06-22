package pt.ua.deti.store.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pt.ua.deti.store.JsonUtils;
import pt.ua.deti.store.entities.*;
import pt.ua.deti.store.security.Jwt;
import pt.ua.deti.store.security.JwtFilter;
import pt.ua.deti.store.security.UserDetailedView;
import pt.ua.deti.store.services.AuthenticationService;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:inmemdb.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Jwt.class)
@AutoConfigureMockMvc
class AuthenticationApiTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtFilter jwtFilter;

    @MockBean
    private AuthenticationService authService;

    @Test
    @DisplayName("Test if we can create an account")
    void testCreateAccount() throws Exception {
        when(authService.register(any(UserRequest.class))).thenReturn(new TokenResponse(
                "TESTTOKEN",
                true
        ));

        UserRequest request = new UserRequest(
                "password",
                "address",
                "email",
                "creditCardNumber",
                123L,
                "creditCardCVC",
                "preferredPickupPointId"
        );

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/auth/register")
                                .contentType("application/json")
                                .content(JsonUtils.toJson(request))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token", is("TESTTOKEN")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)));

        verify(authService, times(1)).register(request);
    }

    @Test
    @DisplayName("Test when register fails")
    void testRegisterFails() throws Exception {
        when(authService.register(any(UserRequest.class))).thenReturn(new TokenResponse(
                "",
                false
        ));

        UserRequest request = new UserRequest(
                "password",
                "address",
                "email",
                "creditCardNumber",
                123L,
                "creditCardCVC",
                "preferredPickupPointId"
        );

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/auth/register")
                                .contentType("application/json")
                                .content(JsonUtils.toJson(request))
                )
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token", is(emptyString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", is(false)));

        verify(authService, times(1)).register(request);
    }

    @Test
    @DisplayName("Test when login succeeds")
    void testLoginSucceeds() throws Exception {
        when(authService.login(any(LoginRequest.class))).thenReturn(new TokenResponse(
                "TESTTOKEN",
                true
        ));

        LoginRequest request = new LoginRequest(
                "email",
                "password"
        );

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/auth/login")
                                .contentType("application/json")
                                .content(JsonUtils.toJson(request))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token", is("TESTTOKEN")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)));

        verify(authService, times(1)).login(request);
    }

    @Test
    @DisplayName("Test when login fails")
    void testLoginFails() throws Exception {
        when(authService.login(any(LoginRequest.class))).thenReturn(new TokenResponse(
                "",
                false
        ));

        LoginRequest request = new LoginRequest(
                "email",
                "password"
        );

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/auth/login")
                                .contentType("application/json")
                                .content(JsonUtils.toJson(request))
                )
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token", is(emptyString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", is(false)));

        verify(authService, times(1)).login(request);
    }

    @Test
    @DisplayName("Test when getting the profile succeeds")
    void testGetProfileSucceeds() throws Exception {
        when(authService.profile(anyString())).thenReturn(new ProfileResponse(
                "address",
                "email",
                "creditCardNumber",
                123L,
                "creditCardCVC",
                new PickupPointResponse(
                        "id",
                        "name",
                        "address"
                )
        ));

        UserDetailedView user = new UserDetailedView("user1", "XXXX");
        String token = Jwt.generateToken(user);
        when(jwtFilter.filter()).thenReturn(true);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/auth/profile")
                                .header("Authorization", "Bearer " + token)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.address", is("address")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", is("email")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.creditCardNumber", is("creditCardNumber")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.creditCardValidity", is(123L)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.creditCardCVC", is("creditCardCVC")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.preferredPickupPoint.pickupPointId", is("id")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.preferredPickupPoint.name", is("name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.preferredPickupPoint.address", is("address")));

        verify(authService, times(1)).profile("user1");
    }

    @Test
    @DisplayName("Test when getting the profile fails due to invalid token")
    void testGetProfileFailsDueToInvalidToken() throws Exception {
        when(authService.profile(anyString())).thenReturn(new ProfileResponse(
                "address",
                "email",
                "creditCardNumber",
                123L,
                "creditCardCVC",
                new PickupPointResponse(
                        "id",
                        "name",
                        "address"
                )
        ));

        UserDetailedView user = new UserDetailedView("user1", "XXXX");
        String token = Jwt.generateToken(user);
        when(jwtFilter.filter()).thenReturn(false);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/auth/profile")
                                .header("Authorization", "Bearer " + token)
                )
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());

        verify(authService, times(0)).profile("user1");
    }

    @Test
    @DisplayName("Test when changing the profile succeeds")
    void testChangeProfileSucceeds() throws Exception {
        when(authService.updateProfile(anyString(), any(UserRequest.class))).thenReturn(new ProfileUpdateResponse(
                true
        ));

        UserDetailedView user = new UserDetailedView("user1", "XXXX");
        String token = Jwt.generateToken(user);
        when(jwtFilter.filter()).thenReturn(true);

        UserRequest request = new UserRequest(
                "address",
                "email",
                "creditCardNumber",
                "creditCardCVC",
                123L,
                "preferredPickupPointId",
                "pickupPointId"
        );

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/auth/profile")
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json")
                        .content(JsonUtils.toJson(request))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)));
    }

    @Test
    @DisplayName("Test when changing the profile fails due to invalid token")
    void testChangeProfileFailsDueToInvalidToken() throws Exception {
        when(authService.updateProfile(anyString(), any(UserRequest.class))).thenReturn(new ProfileUpdateResponse(
                true
        ));

        UserDetailedView user = new UserDetailedView("user1", "XXXX");
        String token = Jwt.generateToken(user);
        when(jwtFilter.filter()).thenReturn(false);

        UserRequest request = new UserRequest(
                "address",
                "email",
                "creditCardNumber",
                "creditCardCVC",
                123L,
                "preferredPickupPointId",
                "pickupPointId"
        );

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/auth/profile")
                                .header("Authorization", "Bearer " + token)
                                .contentType("application/json")
                                .content(JsonUtils.toJson(request))
                )
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());

        verify(authService, times(0)).updateProfile("user1", request);
    }
}
