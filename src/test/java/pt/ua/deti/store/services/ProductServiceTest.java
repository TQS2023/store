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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:inmemdb.properties")
@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    @DisplayName("Test if the product service returns all the entities in the DB.")
    void testFindAll() {
        when(productRepository.findAll()).thenReturn(List.of(
                new ProductEntity("productId", "productName", "productDescription", 10.0),
                new ProductEntity("productId2", "productName2", "productDescription2", 20.0)
        ));

        ProductListingResponse productListingResponses = productService.getListing();

        assertThat(productListingResponses, is(notNullValue()));
        assertThat(productListingResponses.isSuccess(), is(true));
        assertThat(productListingResponses.getProducts().size(), is(2));
        assertThat(productListingResponses.getProducts().get(0).getProductId(), is("productId"));
        assertThat(productListingResponses.getProducts().get(0).getTitle(), is("productName"));
        assertThat(productListingResponses.getProducts().get(0).getDescription(), is("productDescription"));
        assertThat(productListingResponses.getProducts().get(0).getPrice(), is(10.0));

        verify(productRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test if the product service returns an empty list when there are no entities in the DB.")
    void testFindAllEmpty() {
        when(productRepository.findAll()).thenReturn(List.of());

        ProductListingResponse productListingResponses = productService.getListing();

        assertThat(productListingResponses, is(notNullValue()));
        assertThat(productListingResponses.isSuccess(), is(true));
        assertThat(productListingResponses.getProducts().size(), is(0));

        verify(productRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test if the product service returns an error when there is an exception.")
    void testFindAllException() {
        when(productRepository.findAll()).thenReturn(null);

        ProductListingResponse productListingResponses = productService.getListing();

        assertThat(productListingResponses, is(notNullValue()));
        assertThat(productListingResponses.isSuccess(), is(false));
        assertThat(productListingResponses.getProducts().size(), is(0));

        verify(productRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test if we can get a specific product in the DB.")
    void testGetProduct() {
        when(productRepository.findProductEntityById("productId")).thenReturn(
                new ProductEntity("productId", "productName", "productDescription", 10.0)
        );

        ProductResponse product = productService.getProductById("productId");

        assertThat(product, is(notNullValue()));
        assertThat(product.getProductId(), is("productId"));
        assertThat(product.getTitle(), is("productName"));
        assertThat(product.getDescription(), is("productDescription"));
        assertThat(product.getPrice(), is(10.0));

        verify(productRepository, times(1)).findProductEntityById("productId");
    }

    @Test
    @DisplayName("Test if we can get a specific product in the DB when there is no entity found.")
    void testGetProductEmpty() {
        when(productRepository.findProductEntityById("productId")).thenReturn(null);

        ProductResponse product = productService.getProductById("productId");

        assertThat(product, is(nullValue()));

        verify(productRepository, times(1)).findProductEntityById("productId");
    }
}
