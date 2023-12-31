package pt.ua.deti.store.services;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import pt.ua.deti.store.database.*;
import pt.ua.deti.store.entities.CreatePackageResponse;
import pt.ua.deti.store.entities.PackageListingResponse;
import pt.ua.deti.store.entities.ProductListRequest;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:inmemdb.properties")
@SpringBootTest
class OrderingServiceTest {
    @Autowired
    private OrderingService orderingService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private PackageRepository packageRepository;

    @Test
    @DisplayName("Test creating a normal package")
    void testCreatingANormalPackage() {
        when(userRepository.findByEmail("User1")).thenReturn(new UserEntity(
                UUID.randomUUID(), "password", "address", "User1", "creditCardNumber", 1L, "creditCardCVC", null
        ));
        when(packageRepository.save(ArgumentMatchers.any(PackageEntity.class))).thenReturn(new PackageEntity(
            UUID.randomUUID(),
            new UserEntity(
                    UUID.randomUUID(), "password", "address", "User1", "creditCardNumber", 1L, "creditCardCVC", null
            ),

            "status",
            List.of(new PackageProductEntity(
                    UUID.randomUUID(),
                    "Title",
                    "Author",
                    "Description",
                    12.99
                    )
            ),
            "address"
        ));
        UUID productUuid = UUID.randomUUID();
        when(productRepository.findByProductId(productUuid)).thenReturn(new ProductEntity(
                productUuid,
                "Title",
                "Author",
                "Description",
                12.99
                )
        );

        CreatePackageResponse response = orderingService.createPackage("User1", new ProductListRequest(
                List.of(productUuid.toString(), productUuid.toString(), productUuid.toString())
        ));

        assertThat(response, is(notNullValue()));
        assertThat(response.isSuccess(), is(true));
    }

    @Test
    @DisplayName("Test creating a package with no products")
    void testCreatingANormalPackageWithNoProducts() {
        when(userRepository.findByEmail("User1")).thenReturn(new UserEntity(
                "password", "address", "User1", "creditCardNumber", 1L, "creditCardCVC", null
        ));

        CreatePackageResponse response = orderingService.createPackage("User1", new ProductListRequest(
                List.of()
        ));

        assertThat(response, is(notNullValue()));
        assertThat(response.isSuccess(), is(false));
    }

    @Test
    @DisplayName("Test retrieving all my packages")
    void testRetrievingAllMyPackages() {
        when(userRepository.findByEmail("User1")).thenReturn(new UserEntity(
                "password", "address", "User1", "creditCardNumber", 1L, "creditCardCVC", null
        ));
        when(packageRepository.findAllByUserId(new UserEntity(
                "password", "address", "User1", "creditCardNumber", 1L, "creditCardCVC", null
        ))).thenReturn(List.of(new PackageEntity(
                UUID.randomUUID(),
                new UserEntity(
                        "password", "address", "User1", "creditCardNumber", 1L, "creditCardCVC", null
                        ),
                "status",
                List.of(
                        new PackageProductEntity(
                                UUID.randomUUID(),
                                "Title",
                                "Author",
                                "Description",
                                12.99
                        )
                ),
                "address"
        )));

        PackageListingResponse packages = orderingService.myPackages("User1");

        assertThat(packages, is(notNullValue()));
        assertThat(packages.isSuccess(), is(true));

        verify(userRepository, times(1)).findByEmail("User1");
        verify(packageRepository, times(1)).findAllByUserId(ArgumentMatchers.any(UserEntity.class));
    }
}
