package pt.ua.deti.store.database;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PackageRepository extends JpaRepository<PackageEntity, String> {
    List<PackageEntity> findAllByUserId(UserEntity user);
    PackageEntity findByPackageId(UUID uuid);
}
