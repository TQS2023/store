package pt.ua.deti.store.database;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    private UUID productId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "description", nullable = false, columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "price", nullable = false)
    private Double price;

    public ProductEntity() {

    }

    public ProductEntity(String title, String author, String description, double price) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
    }

    public ProductEntity(UUID productId, String title, String author, String description, double price) {
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
