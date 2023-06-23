package pt.ua.deti.store.database;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PackageRepository extends JpaRepository<PackageEntity, String> {
}
