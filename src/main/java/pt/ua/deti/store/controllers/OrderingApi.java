package pt.ua.deti.store.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pt.ua.deti.store.entities.CreatePackageResponse;
import pt.ua.deti.store.entities.PackageListingResponse;
import pt.ua.deti.store.entities.ProductListRequest;
import pt.ua.deti.store.security.Jwt;
import pt.ua.deti.store.services.OrderingService;

@RestController
@RequestMapping("/api/order")
public class OrderingApi {
    private final OrderingService service;

    public OrderingApi(OrderingService service) {
        this.service = service;
    }


    @PreAuthorize("@jwtFilter.filter()")
    @PostMapping("/create")
    public CreatePackageResponse createOrder(@RequestHeader("Authorization") String token, @RequestBody ProductListRequest productListRequest) {
        String user = Jwt.getSubject(token.replace("Bearer ", ""));
        return service.createPackage(user, productListRequest);
    }

    @PreAuthorize("@jwtFilter.filter()")
    @GetMapping("/my")
    public PackageListingResponse myPackages(@RequestHeader("Authorization") String token) {
        String user = Jwt.getSubject(token.replace("Bearer ", ""));
        return service.myPackages(user);
    }
}
