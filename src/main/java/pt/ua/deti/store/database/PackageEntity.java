package pt.ua.deti.store.database;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "package")
public class PackageEntity {
    @Id
    @Column(name = "package_id")
    private UUID packageId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity userId;

    @Column(name = "status")
    private String status;

    @Column(name = "products")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PackageProductEntity> products;

    @Column(name = "address")
    private String address;

    public PackageEntity(UUID packageId, UserEntity userId, String status, List<PackageProductEntity> products, String address) {
        this.packageId = packageId;
        this.userId = userId;
        this.status = status;
        this.products = products;
        this.address = address;
    }

    public PackageEntity() {

    }

    public UUID getPackageId() {
        return packageId;
    }

    public UserEntity getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public List<PackageProductEntity> getProducts() {
        return products;
    }

    public String getAddress() {
        return address;
    }
}
