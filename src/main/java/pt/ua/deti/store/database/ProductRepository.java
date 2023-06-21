package pt.ua.deti.store.database;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    ProductEntity findProductEntityById(String id);
}
