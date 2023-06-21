package pt.ua.deti.store.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pt.ua.deti.store.entities.ProductListingResponse;

@RestController("/product")
public class ProductApi {
    @GetMapping("/product/all")
    public ProductListingResponse getAllProducts() {
        return null;
    }

    @GetMapping("/product/{id}")
    public ProductListingResponse getProductById(@PathVariable String id) {
        return null;
    }
}
