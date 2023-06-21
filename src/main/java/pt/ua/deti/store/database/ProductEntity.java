package pt.ua.deti.store.database;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    public ProductEntity() {

    }

    public ProductEntity(String id, String title, String description, double price) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.id = id;
    }
}
