package com.turion.resources.repo;

import com.turion.resources.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepo extends JpaRepository<Image, Integer> {

    List<Image> findAllByRequestId(Integer requestId);
}
