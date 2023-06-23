package pt.ua.deti.store.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ua.deti.store.entities.CreatePackageResponse;
import pt.ua.deti.store.entities.PackageListingResponse;
import pt.ua.deti.store.entities.ProductListRequest;
import pt.ua.deti.store.entities.ProductListingResponse;

@RestController
@RequestMapping("/api/order")
public class OrderingApi {
    @PreAuthorize("@jwtFilter.filter()")
    @GetMapping("/create")
    public CreatePackageResponse createOrder(@RequestBody ProductListRequest productListRequest) {
        return null;
    }

    @PreAuthorize("@jwtFilter.filter()")
    @GetMapping("/my")
    public PackageListingResponse myPackages() {
        return null;
    }
}
