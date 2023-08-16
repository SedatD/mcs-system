package com.turion.resources.repo;

import com.turion.resources.entity.SatData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SatRepo extends JpaRepository<SatData, Integer> {
}
