package com.house.transport.security.token;

import com.house.transport.model.Customer;
import com.house.transport.model.Mover;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByCustomer(Customer customer);
    void deleteByMover(Mover mover);
}