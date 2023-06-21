package pt.ua.deti.store.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ua.deti.store.entities.ProductListingResponse;
import pt.ua.deti.store.entities.ProductResponse;
import pt.ua.deti.store.services.ProductService;

@RestController
@RequestMapping("/api")
public class ProductApi {
    private ProductService productService;

    public ProductApi(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/all")
    public ProductListingResponse getAllProducts() {
        return productService.getListing();
    }

    @GetMapping("/product/{id}")
    public ProductResponse getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }
}
