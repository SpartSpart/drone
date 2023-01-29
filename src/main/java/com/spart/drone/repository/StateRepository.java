package com.spart.drone.repository;

import com.spart.drone.repository.model.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<StateEntity,Long> {
    Optional<StateEntity> findByName(String name);
}
