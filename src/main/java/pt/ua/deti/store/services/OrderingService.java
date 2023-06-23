package pt.ua.deti.store.services;

import org.springframework.stereotype.Service;
import pt.ua.deti.store.entities.CreatePackageResponse;
import pt.ua.deti.store.entities.PackageListingResponse;
import pt.ua.deti.store.entities.ProductListRequest;
import pt.ua.deti.store.entities.ProductListingResponse;

@Service
public class OrderingService {
    public CreatePackageResponse createPackage(String user, ProductListRequest productListRequest) {
        return null;
    }

    public PackageListingResponse myPackages(String user) {
        return null;
    }
}
