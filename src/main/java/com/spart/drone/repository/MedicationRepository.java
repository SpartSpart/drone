package com.spart.drone.repository;

import com.spart.drone.repository.model.MedicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicationRepository extends JpaRepository<MedicationEntity, Long> {
    Optional<MedicationEntity> findById(Long medicationId);
}
