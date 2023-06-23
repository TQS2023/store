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
import pt.ua.deti.store.entities.CreatePackageResponse;
import pt.ua.deti.store.entities.PackageListingResponse;
import pt.ua.deti.store.entities.PackageResponse;
import pt.ua.deti.store.entities.ProductListRequest;
import pt.ua.deti.store.picky.Api;
import pt.ua.deti.store.security.Jwt;
import pt.ua.deti.store.security.JwtFilter;
import pt.ua.deti.store.security.UserDetailedView;
import pt.ua.deti.store.services.OrderingService;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:inmemdb.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class OrderingApiTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtFilter jwtFilter;

    @MockBean
    private OrderingService orderingService;

    @Test
    @DisplayName("Test if we can create an order normally")
    void testCreateOrder() throws Exception {
        when(orderingService.createPackage(anyString(), any(ProductListRequest.class))).thenReturn(
                new CreatePackageResponse(true)
        );

        UserDetailedView user = new UserDetailedView("user1", "XXXX");
        String token = Jwt.generateToken(user);
        when(jwtFilter.filter()).thenReturn(true);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/ordering")
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json")
                        .content(JsonUtils.toJson(new ProductListRequest(List.of("Product1", "Product2"))))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)));

        verify(orderingService, times(1)).createPackage(anyString(), any(ProductListRequest.class));
    }

    @Test
    @DisplayName("Test if when creating an order an error occurs")
    void testCreateOrderNoSuccess() throws Exception {
        when(orderingService.createPackage(anyString(), any(ProductListRequest.class))).thenReturn(
                new CreatePackageResponse(false)
        );

        UserDetailedView user = new UserDetailedView("user1", "XXXX");
        String token = Jwt.generateToken(user);
        when(jwtFilter.filter()).thenReturn(true);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/ordering")
                                .header("Authorization", "Bearer " + token)
                                .contentType("application/json")
                                .content(JsonUtils.toJson(new ProductListRequest(List.of("Product1", "Product2"))))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", is(false)));

        verify(orderingService, times(1)).createPackage(anyString(), any(ProductListRequest.class));
    }

    @Test
    @DisplayName("Test creating an order with an invalid or missing token")
    void testCreateOrderInvalidToken() throws Exception {
        when(jwtFilter.filter()).thenReturn(false);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/ordering")
                                .header("Authorization", "Bearer XXXX")
                                .contentType("application/json")
                                .content(JsonUtils.toJson(new ProductListRequest(List.of("Product1", "Product2"))))
                )
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());

        verify(orderingService, never()).createPackage(anyString(), any(ProductListRequest.class));
    }

    @Test
    @DisplayName("Test retrieving our orders")
    void testGetOrders() throws Exception {
        when(orderingService.myPackages(any(String.class))).thenReturn(
                new PackageListingResponse(
                        true,
                        List.of(
                                new PackageResponse(
                                        "packageId",
                                        "User1",
                                        "INTRANSIT",
                                        List.of("Product1", "Product2"),
                                        "address1"
                                ), new PackageResponse(
                                        "packageId",
                                        "User1",
                                        "DELIVERED",
                                        List.of("Product1", "Product2"),
                                        "address1"
                                )
                        )
                )
        );

        UserDetailedView user = new UserDetailedView("user1", "XXXX");
        String token = Jwt.generateToken(user);
        when(jwtFilter.filter()).thenReturn(true);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/ordering")
                                .header("Authorization", "Bearer " + token)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)));

        verify(orderingService, times(1)).myPackages(any(String.class));
    }

    @Test
    @DisplayName("Test retrieving our orders with an invalid or missing token")
    void testGetOrdersInvalidToken() throws Exception {
        when(jwtFilter.filter()).thenReturn(false);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/ordering")
                                .header("Authorization", "Bearer XXXX")
                )
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());

        verify(orderingService, never()).myPackages(any(String.class));
    }
}
