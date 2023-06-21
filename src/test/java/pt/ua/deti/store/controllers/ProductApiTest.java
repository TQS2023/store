package pt.ua.deti.store.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pt.ua.deti.store.entities.ProductListingResponse;
import pt.ua.deti.store.entities.ProductResponse;
import pt.ua.deti.store.services.ProductService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:inmemdb.properties")
@WebMvcTest(ProductApi.class)
public class ProductApiTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("Test if the listing returns all products correctly.")
    void testListing() throws Exception {
        when(productService.getListing()).thenReturn(new ProductListingResponse(List.of(
                new ProductResponse("productID", "Product title", "Product description", 12.99)
        ), true));

        mockMvc.perform(
                    MockMvcRequestBuilders.get("/api/product/all").contentType("application/json")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].productId").value("productID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].title").value("Product title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].description").value("Product description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].price").value(12.99));

        verify(productService, times(1)).getListing();
    }

    @Test
    @DisplayName("Test if the listing returns an empty list when there are no products.")
    void testListingEmpty() throws Exception {
        when(productService.getListing()).thenReturn(new ProductListingResponse(new ArrayList<>(), false));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/product/all").contentType("application/json")
                )
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products", hasSize(0)));

        verify(productService, times(1)).getListing();
    }

    @Test
    @DisplayName("Test if the listing returns an error when there is an error.")
    void testListingError() throws Exception {
        when(productService.getListing()).thenReturn(new ProductListingResponse(null, false));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/product/all").contentType("application/json")
                )
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));

        verify(productService, times(1)).getListing();
    }

    @Test
    @DisplayName("Test if we get something even if the service returns null")
    void testListingNull() throws Exception {
        when(productService.getListing()).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/product/all").contentType("application/json")
                )
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));

        verify(productService, times(1)).getListing();
    }

    @Test
    @DisplayName("Test if we are able to retrieve a product by its ID.")
    void testGetProduct() throws Exception {
        when(productService.getProductById("productID")).thenReturn(new ProductResponse("productID", "Product title", "Product description", 12.99));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/product/productID").contentType("application/json")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value("productID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Product title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Product description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(12.99));

        verify(productService, times(1)).getProductById(anyString());
    }

    @Test
    @DisplayName("Test if we are able to retrieve a product by its ID when there is an error.")
    void testGetProductError() throws Exception {
        when(productService.getProductById("productID")).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/product/productID").contentType("application/json")
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(productService, times(1)).getProductById(anyString());
    }
}
