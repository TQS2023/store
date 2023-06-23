package pt.ua.deti.store.database;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PickupPointRepository extends JpaRepository<PickupPointEntity, Long> {
    PickupPointEntity findByPickupPointId(UUID uuid);

    boolean existsByPickupPointId(UUID uuid);
}