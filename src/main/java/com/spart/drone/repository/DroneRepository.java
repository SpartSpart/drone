package com.spart.drone.repository;

import com.spart.drone.repository.model.DroneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DroneRepository extends JpaRepository<DroneEntity, Long> {
    Optional<DroneEntity> getDroneById(Long droneId);


    @Query(value = "SELECT * FROM drone\n" +
            "WHERE id NOT IN (\n" +
            "    SELECT drone_id FROM drone_medication)", nativeQuery = true)
    Optional<List<DroneEntity>> findDronesWithoutMedication();
}
