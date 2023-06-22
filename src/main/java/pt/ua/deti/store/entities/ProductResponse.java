package pt.ua.deti.store.entities;

import pt.ua.deti.store.database.ProductEntity;

public class ProductResponse {
    private final String productId;
    private final String title;
    private final String description;
    private final double price;
    private final String author;

    public ProductResponse(String productId, final String title, final String description, final double price, final String author) {
        this.productId = productId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.author = author;
    }

    public ProductResponse(ProductEntity productEntity) {
        this(productEntity.getProductId().toString(), productEntity.getTitle(), productEntity.getDescription(), productEntity.getPrice(), productEntity.getAuthor());
    }

    public String getProductId() {
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
