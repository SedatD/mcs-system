package com.turion.resources.repo;

import com.turion.resources.entity.Image;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface ImageRepo extends JpaRepository<Image, Integer> {

    Optional<Image> findByRequestId(Integer requestId);
}
