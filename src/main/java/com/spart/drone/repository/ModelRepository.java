package com.spart.drone.repository;

import com.spart.drone.repository.model.ModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<ModelEntity, Long> {
   Optional<ModelEntity> findByName (String name);
}
