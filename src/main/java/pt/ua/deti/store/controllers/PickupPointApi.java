package pt.ua.deti.store.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ua.deti.store.entities.PickupPointListingResponse;

@RestController
@RequestMapping("/api/pickuppoints")
public class PickupPointApi {
    @GetMapping("all")
    public PickupPointListingResponse getAll() {
        return null;
    }
}
