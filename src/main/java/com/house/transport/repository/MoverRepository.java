package com.house.transport.repository;

import com.house.transport.model.Mover;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MoverRepository extends JpaRepository<Mover, Long> {
    Optional<Mover> findByEmail(String email);
}
