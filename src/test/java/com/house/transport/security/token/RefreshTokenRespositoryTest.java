package com.house.transport.security.token;

import com.house.transport.config.TestApplication;
import com.house.transport.model.Customer;
import com.house.transport.model.Mover;
import com.house.transport.repository.CustomerRepository;
import com.house.transport.repository.MoverRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = TestApplication.class)
@Transactional
public class RefreshTokenRespositoryTest {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MoverRepository moverRepository;

    @Test
    public void addTokenSuccessfully(){
        RefreshToken token = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusSeconds(86400))
                .build();
        RefreshToken savedRefreshToken = refreshTokenRepository.save(token);
        assertThat(savedRefreshToken).extracting(
                RefreshToken::getToken,
                RefreshToken::getExpiryDate
        ).containsExactly(
                token.getToken(),
                token.getExpiryDate()
        );
    }

    @Test
    public void deleteTokenByCustomer(){
        Customer customer = new Customer(null, "Muhammed","Kalabasi","muhammedkalabasi@gmail.com","5343343434","Password_1234");
        customerRepository.save(customer);
        Customer foundCustomer = customerRepository.findByEmail("muhammedkalabasi@gmail.com").orElseThrow();
        RefreshToken token = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusSeconds(86400))
                .customer(foundCustomer).build();
        refreshTokenRepository.save(token);
        refreshTokenRepository.deleteByCustomer(customer);
        Optional<RefreshToken> foundRefreshToken = refreshTokenRepository.findById(1L);
        assertThat(foundRefreshToken).isNotPresent();
    }

    @Test
    public void deleteTokenByMover(){
        Mover mover = new Mover(
                null,
                "testmover@example.com",
                "05343343434",
                "TestParola_1234",
                "Kalabasi Company",
                "examplelogo.png",
                "sample about text",
                "Sample information",
                new byte[0],
                new byte[0],
                "1234567890",
                2,
                null
        );
        moverRepository.save(mover);
        Mover foundMover = moverRepository.findByEmail("testmover@example.com").orElseThrow();
        RefreshToken token = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusSeconds(86400))
                .mover(foundMover).build();
        refreshTokenRepository.save(token);
        refreshTokenRepository.deleteByMover(mover);
        Optional<RefreshToken> foundRefreshToken = refreshTokenRepository.findById(1L);
        assertThat(foundRefreshToken).isNotPresent();
    }

    @Test
    public void findTokenEntityByRefreshTokenAndSuccessful(){
        RefreshToken token = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusSeconds(86400))
                .build();
        RefreshToken savedRefreshToken = refreshTokenRepository.saveAndFlush(token);
        Optional<RefreshToken> foundRefreshToken = refreshTokenRepository.findById(savedRefreshToken.getId());
        assertThat(foundRefreshToken).isPresent().get()
                .extracting(RefreshToken::getToken, RefreshToken::getExpiryDate)
                .containsExactly(savedRefreshToken.getToken(), savedRefreshToken.getExpiryDate());
    }
}
