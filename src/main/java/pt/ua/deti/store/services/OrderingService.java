package pt.ua.deti.store.services;

import org.springframework.stereotype.Service;
import pt.ua.deti.store.database.*;
import pt.ua.deti.store.entities.CreatePackageResponse;
import pt.ua.deti.store.entities.PackageListingResponse;
import pt.ua.deti.store.entities.PackageResponse;
import pt.ua.deti.store.entities.ProductListRequest;

import java.util.UUID;

@Service
public class OrderingService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PackageRepository packageRepository;

    public OrderingService(UserRepository userRepository, ProductRepository productRepository, PackageRepository packageRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.packageRepository = packageRepository;
    }

    public CreatePackageResponse createPackage(String user, ProductListRequest productListRequest) {
        if (productListRequest.getProducts().isEmpty()) {
            return new CreatePackageResponse(false);
        }

        UserEntity userEntity = userRepository.findByEmail(user);
        PackageEntity packageEntity = new PackageEntity(
                null,
                userEntity,
                "INTRANSIT",
                productListRequest.getProducts().stream().map(product -> productRepository.findByProductId(UUID.fromString(product))).toList(),
                userEntity.getAddress()
        );
        packageRepository.save(packageEntity);

        return new CreatePackageResponse(true);
    }

    public PackageListingResponse myPackages(String user) {
        UserEntity userEntity = userRepository.findByEmail(user);
        return new PackageListingResponse(
                true,
                packageRepository.findAllByUserId(userEntity).stream().map(pkg -> new PackageResponse(
                        pkg.getPackageId().toString(),
                        pkg.getUserId().getUserId().toString(),
                        pkg.getStatus(),
                        pkg.getProducts().stream().map(product -> product.getProductId().toString()).toList(),
                        pkg.getAddress()
                )).toList()
        );
    }
}
