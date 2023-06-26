package pt.ua.deti.store.database;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "package_product")
public class PackageProductEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    private UUID packageProductId;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "description", nullable = false, columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "price", nullable = false)
    private Double price;

    public PackageProductEntity() {

    }

    public PackageProductEntity(ProductEntity entity) {
        this.productId = entity.getProductId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
    }

    public PackageProductEntity(String title, String author, String description, double price) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
    }

    public PackageProductEntity(UUID productId, String title, String author, String description, double price) {
        this.productId = productId;
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
    }

    public UUID getProductId() {
        return productId;
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getAuthor() {
        return author;
    }
}
