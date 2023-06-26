package pt.ua.deti.store.services;

import org.springframework.stereotype.Service;
import pt.ua.deti.store.database.*;
import pt.ua.deti.store.entities.CreatePackageResponse;
import pt.ua.deti.store.entities.PackageListingResponse;
import pt.ua.deti.store.entities.PackageResponse;
import pt.ua.deti.store.entities.ProductListRequest;
import pt.ua.deti.store.picky.Api;
import pt.ua.deti.store.picky.PickyPackageRequest;

import java.util.List;
import java.util.UUID;

@Service
public class OrderingService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PackageRepository packageRepository;
    private final Api pickyApi;

    public OrderingService(UserRepository userRepository, ProductRepository productRepository, PackageRepository packageRepository, Api pickyApi) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.packageRepository = packageRepository;
        this.pickyApi = pickyApi;
    }

    public CreatePackageResponse createPackage(String user, ProductListRequest productListRequest) {
        if (productListRequest.getProducts().isEmpty()) {
            return new CreatePackageResponse(false);
        }

        UserEntity userEntity = userRepository.findByEmail(user);
        List<ProductEntity> products = productListRequest.getProducts().stream().map(product -> productRepository.findByProductId(UUID.fromString(product))).toList();

        PickyPackageRequest pickyPackage = new PickyPackageRequest(
                userEntity.getUserId().toString(),
                "INTRANSIT",
                products.stream().map(product -> product.getProductId().toString()).toArray(String[]::new),
                userEntity.getAddress()
        );
        pickyApi.createPackage(pickyPackage);
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
