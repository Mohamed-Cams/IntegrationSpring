package com.integration.bde.Repositories;

import com.integration.bde.Entities.Depute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeputeRepository extends JpaRepository<Depute, Long> {
  Optional<Depute> findById(Long id);
}