package pt.ua.deti.store.services;

import org.springframework.stereotype.Service;
import pt.ua.deti.store.database.ProductEntity;
import pt.ua.deti.store.database.ProductRepository;
import pt.ua.deti.store.entities.ProductResponse;
import pt.ua.deti.store.entities.ProductListingResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public ProductListingResponse getListing() {
        List<ProductEntity> productEntities = repository.findAll();
        if (productEntities.isEmpty()) {
            return new ProductListingResponse(new ArrayList<>(), false);
        }

        List<ProductResponse> productResponses = productEntities.stream()
                .map(ProductResponse::new)
                .toList();

        return new ProductListingResponse(productResponses, true);
    }

    public ProductResponse getProductById(String productId) {
        ProductEntity productEntity = repository.findProductEntityById(UUID.fromString(productId));
        if (productEntity == null) {
            return null;
        }

        return new ProductResponse(productEntity);
    }
}
