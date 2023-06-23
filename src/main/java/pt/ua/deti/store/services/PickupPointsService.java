package pt.ua.deti.store.services;

import org.springframework.stereotype.Service;
import pt.ua.deti.store.database.PickupPointRepository;
import pt.ua.deti.store.entities.PickupPointListingResponse;
import pt.ua.deti.store.entities.PickupPointResponse;

@Service
public class PickupPointsService {
    private final PickupPointRepository pickupPointsRepository;

    public PickupPointsService(PickupPointRepository pickupPointsRepository) {
        this.pickupPointsRepository = pickupPointsRepository;
    }

    public PickupPointListingResponse getAll() {
        return new PickupPointListingResponse(pickupPointsRepository
                .findAll()
                .stream()
                .map(pickupPoint -> new PickupPointResponse(
                        pickupPoint.getPickupPointId().toString(),
                        pickupPoint.getName(),
                        pickupPoint.getAddress()
                ))
                .toList(),
                true
        );
    }
}
