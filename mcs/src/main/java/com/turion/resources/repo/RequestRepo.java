package com.turion.resources.repo;

import com.turion.resources.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequestRepo extends JpaRepository<Request, Integer> {

    Optional<Request> findTopByOrderByIdDesc();
    Optional<Request> findFirstByOrderByIdAsc();
}
