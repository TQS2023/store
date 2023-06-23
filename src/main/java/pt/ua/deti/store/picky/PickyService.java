package pt.ua.deti.store.picky;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pt.ua.deti.store.database.PickupPointEntity;
import pt.ua.deti.store.database.PickupPointRepository;

import java.util.Arrays;
import java.util.UUID;

@Service
public class PickyService {
    private final Api picky;
    private final PickupPointRepository pickupPointRepository;

    public PickyService(Api picky, PickupPointRepository pickupPointRepository) {
        this.picky = picky;
        this.pickupPointRepository = pickupPointRepository;
    }

    @Scheduled(fixedRate = 1000)
    public void update() {
        PickyPickupPoints pickyPickupPoints = picky.getPickupPoints();

        if (pickyPickupPoints == null) {
            return;
        }

        Arrays.stream(pickyPickupPoints.getPickupPoints()).forEach(p -> {
            PickupPointEntity pEnt = new PickupPointEntity(
                    UUID.fromString(p.getId()),
                    p.getName(),
                    p.getAddress()
            );

            pickupPointRepository.save(pEnt);
        });
    }
}
