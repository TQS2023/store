package pt.ua.deti.store.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import pt.ua.deti.store.database.PickupPointEntity;
import pt.ua.deti.store.database.PickupPointRepository;
import pt.ua.deti.store.entities.PickupPointListingResponse;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:inmemdb.properties")
@SpringBootTest
public class PickupPointsServiceTest {
    @Autowired
    private PickupPointsService pickupPointsService;

    @MockBean
    private PickupPointRepository pickupPointsRepository;

    @Test
    @DisplayName("Test if PickupPointsService is working")
    public void testPickupPointsService() {
        when(pickupPointsRepository.findAll()).thenReturn(
                List.of(
                        new PickupPointEntity(UUID.randomUUID(), "name1", "addr1"),
                        new PickupPointEntity(UUID.randomUUID(), "name2", "addr2"),
                        new PickupPointEntity(UUID.randomUUID(), "name3", "addr3")
                )
        );

        PickupPointListingResponse pickupPointListingResponse = pickupPointsService.getAll();

        assertThat(pickupPointListingResponse, is(notNullValue()));
        assertThat(pickupPointListingResponse.getPickupPoints().size(), is(3));
        assertThat(pickupPointListingResponse.isSuccess(), is(true));
    }
}
