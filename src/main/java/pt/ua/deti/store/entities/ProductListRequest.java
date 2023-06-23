package pt.ua.deti.store.entities;

import java.util.List;

public class ProductListRequest {
    private final List<String> products;

    public ProductListRequest(List<String> products) {
        this.products = products;
    }

    public List<String> getProducts() {
        return products;
    }
}
