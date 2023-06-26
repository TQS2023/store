package pt.ua.deti.store.picky;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pt.ua.deti.store.database.*;

import java.util.Arrays;
import java.util.UUID;

@Service
public class PickyService {
    private final Api picky;
    private final PickupPointRepository pickupPointRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PackageRepository packageRepository;

    public PickyService(Api picky, PickupPointRepository pickupPointRepository, UserRepository userRepository, ProductRepository productRepository, PackageRepository packageRepository) {
        this.picky = picky;
        this.pickupPointRepository = pickupPointRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.packageRepository = packageRepository;
    }

    @Scheduled(fixedRate = 1000)
    public void updatePickupPoints() {
        PickyPickupPoints pickyPickupPoints = picky.getPickupPoints();

        if (pickyPickupPoints == null) {
            return;
        }

        Arrays.stream(pickyPickupPoints.getPickupPoints()).forEach(p -> {
            if (pickupPointRepository.existsByPickupPointId(UUID.fromString(p.getId()))) {
                return;
            }

            PickupPointEntity pEnt = new PickupPointEntity(
                    UUID.fromString(p.getId()),
                    p.getName(),
                    p.getAddress()
            );

            pickupPointRepository.save(pEnt);
        });
    }

    @Scheduled(fixedRate = 1000)
    public void updatePackages() {
        PickyAllPackages pickyPackages = picky.getAllPackages();

        if (pickyPackages == null) {
            return;
        }

        Arrays.stream(pickyPackages.getPackages()).forEach(p -> {
            if (packageRepository.existsByPackageId(UUID.fromString(p.getPackageId()))) {
                return;
            }

            PackageEntity pEnt = new PackageEntity(
                    UUID.fromString(p.getPackageId()),
                    userRepository.findByUserId(UUID.fromString(p.getUserId())),
                    p.getStatus(),
                    Arrays.stream(p.getItems()).map(product -> new PackageProductEntity(productRepository.findByProductId(UUID.fromString(product)))).toList(),
                    p.getAddress()
            );

            packageRepository.save(pEnt);
        });
    }
}
