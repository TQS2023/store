package pt.ua.deti.store.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ua.deti.store.entities.PickupPointListingResponse;
import pt.ua.deti.store.services.PickupPointsService;

@RestController
@RequestMapping("/api/pickuppoints")
public class PickupPointApi {
    private final PickupPointsService pickupPointsService;

    public PickupPointApi(PickupPointsService pickupPointsService) {
        this.pickupPointsService = pickupPointsService;
    }

    @GetMapping("all")
    public ResponseEntity<PickupPointListingResponse> getAll() {
        PickupPointListingResponse pickupPointListingResponse = pickupPointsService.getAll();
        if (Boolean.FALSE.equals(pickupPointListingResponse.isSuccess())) {
            return ResponseEntity.internalServerError().body(pickupPointListingResponse);
        }

        return ResponseEntity.ok(pickupPointListingResponse);
    }
}
