package com.house.transport.security.token;

import com.house.transport.model.Customer;
import com.house.transport.model.Mover;
import com.house.transport.repository.CustomerRepository;
import com.house.transport.repository.MoverRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MoverRepository moverRepository;

    private static final Logger logger = LogManager.getLogger(RefreshTokenService.class);

    @Transactional
    public RefreshToken createRefreshTokenForCustomer(Customer customer){
        logger.info("[RefreshTokenService][createRefreshTokenForCustomer] Customer name: "+customer.getName());

        RefreshToken refreshToken = RefreshToken.builder()
                        .token(UUID.randomUUID().toString())
                        .expiryDate(Instant.now().plusSeconds(86400))
                        .customer(customer).build();
        logger.info("[RefreshTokenService][createRefreshTokenForCustomer] refreshToken: "+refreshToken);

        refreshTokenRepository.deleteByCustomer(customer);
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Transactional
    public RefreshToken createRefreshTokenForMover(Mover mover){
        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusSeconds(86400))
                .mover(mover).build();
        refreshTokenRepository.deleteByMover(mover);
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public boolean validateRefreshToken(String token){
        return refreshTokenRepository.findByToken(token)
                .filter(rt -> rt.getExpiryDate().isAfter(Instant.now()))
                .isPresent();
    }

    public void deleteRefreshToken(String token){
        refreshTokenRepository.findByToken(token).ifPresent(refreshTokenRepository::delete);
    }

    public RefreshToken findToken(String token){
        return refreshTokenRepository.findByToken(token).orElseThrow(()->new RuntimeException("Invalid Refresh Token"));
    }
}
