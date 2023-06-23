package pt.ua.deti.store.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import pt.ua.deti.store.database.ProductEntity;
import pt.ua.deti.store.database.ProductRepository;
import pt.ua.deti.store.entities.ProductListingResponse;
import pt.ua.deti.store.entities.ProductResponse;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:inmemdb.properties")
@SpringBootTest
class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    @DisplayName("Test if the product service returns all the entities in the DB.")
    void testFindAll() {
        when(productRepository.findAll()).thenReturn(List.of(
                new ProductEntity(UUID.fromString("02f64885-54f1-4a6e-9e18-4a6ddbaee13c"), "productName", "productAuthor", "productDescription", 10.0),
                new ProductEntity(UUID.fromString("02f64885-54f2-4a6e-9e18-4a6ddbaee13c"), "productName2", "productAuthor2", "productDescription2", 20.0)
        ));

        ProductListingResponse productListingResponses = productService.getListing();

        assertThat(productListingResponses, is(notNullValue()));
        assertThat(productListingResponses.isSuccess(), is(true));
        assertThat(productListingResponses.getProducts().size(), is(2));

        verify(productRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test if the product service returns an empty list when there are no entities in the DB.")
    void testFindAllEmpty() {
        when(productRepository.findAll()).thenReturn(List.of());

        ProductListingResponse productListingResponses = productService.getListing();

        assertThat(productListingResponses, is(notNullValue()));
        assertThat(productListingResponses.isSuccess(), is(false));
        assertThat(productListingResponses.getProducts().size(), is(0));

        verify(productRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test if we can get a specific product in the DB.")
    void testGetProduct() {
        UUID uuid = UUID.randomUUID();
        when(productRepository.findByProductId(uuid)).thenReturn(
                new ProductEntity(uuid, "productName", "productAuthor", "productDescription", 10.0)
        );

        ProductResponse product = productService.getProductById(uuid.toString());

        assertThat(product, is(notNullValue()));
        assertThat(product.getProductId(), is(uuid.toString()));
        assertThat(product.getTitle(), is("productName"));
        assertThat(product.getDescription(), is("productDescription"));
        assertThat(product.getPrice(), is(10.0));

        verify(productRepository, times(1)).findByProductId(uuid);
    }

    @Test
    @DisplayName("Test if we can get a specific product in the DB when there is no entity found.")
    void testGetProductEmpty() {
        UUID uuid = UUID.randomUUID();
        when(productRepository.findByProductId(uuid)).thenReturn(null);

        ProductResponse product = productService.getProductById(uuid.toString());

        assertThat(product, is(nullValue()));

        verify(productRepository, times(1)).findByProductId(uuid);
    }
}
