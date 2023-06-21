package pt.ua.deti.store.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ua.deti.store.entities.ProductListingResponse;
import pt.ua.deti.store.entities.ProductResponse;
import pt.ua.deti.store.services.ProductService;

import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class ProductApi {
    private final ProductService productService;

    public ProductApi(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/all")
    public ResponseEntity<ProductListingResponse> getAllProducts() {
        ProductListingResponse result = productService.getListing();

        if (result == null) {
            result = new ProductListingResponse(new ArrayList<>(), false);
        }

        if (!result.isSuccess()) {
            return ResponseEntity.internalServerError().body(result);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable String id) {
        ProductResponse result = productService.getProductById(id);

        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
}
