package pt.ua.deti.store.entities;

public class ProductResponse {
    private final String productId;
    private final String title;
    private final String description;
    private final double price;

    public ProductResponse(String productId, final String title, final String description, final double price) {
        this.productId = productId;
        this.title = title;
        this.description = description;
        this.price = price;
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
}
