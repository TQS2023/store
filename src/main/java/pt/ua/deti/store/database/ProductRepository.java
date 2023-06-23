package pt.ua.deti.store.database;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    ProductEntity findByProductId(UUID id);
}
