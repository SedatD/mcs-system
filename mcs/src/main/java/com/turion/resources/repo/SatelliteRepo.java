package com.turion.resources.repo;

import com.turion.resources.entity.Satellite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SatelliteRepo extends JpaRepository<Satellite, Integer> {
}
