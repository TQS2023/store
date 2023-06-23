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
import pt.ua.deti.store.entities.PickupPointListingResponse;
import pt.ua.deti.store.entities.PickupPointResponse;
import pt.ua.deti.store.entities.ProductListingResponse;
import pt.ua.deti.store.services.PickupPointsService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:inmemdb.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class PickupPointsApiTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PickupPointsService pickupPointsService;

    @Test
    @DisplayName("Test if we can corretly list all the pickup points")
    void listAllPickupPoints() throws Exception {
        when(pickupPointsService.getAll()).thenReturn(new PickupPointListingResponse(
                List.of(
                        new PickupPointResponse(
                                "pickupId",
                                "pickupName",
                                "address"
                        ),
                        new PickupPointResponse(
                                "pickupId2",
                                "pickupName2",
                                "address2"
                        )
                ),
                true
        ));

        mockMvc.perform(MockMvcRequestBuilders.get("/pickup-points"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)));

        verify(pickupPointsService, times(1)).getAll();
    }

    @Test
    @DisplayName("Test if we handle an error correctly")
    void handleErrors() throws Exception {
        when(pickupPointsService.getAll()).thenReturn(new PickupPointListingResponse(
                List.of(),
                false
        ));

        mockMvc.perform(MockMvcRequestBuilders.get("/pickup-points"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());

        verify(pickupPointsService, times(1)).getAll();
    }
}
