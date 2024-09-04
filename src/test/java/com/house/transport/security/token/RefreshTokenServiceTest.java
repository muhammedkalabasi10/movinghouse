package com.house.transport.security.token;

import com.house.transport.config.TestApplication;
import com.house.transport.model.Customer;
import com.house.transport.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.Optional;

@Testcontainers
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = TestApplication.class)
public class RefreshTokenServiceTest {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void createRefreshTokenForCustomerAndDeletePreviousTokenSuccess(){
        Customer customer = new Customer(null, "Muhammed","Kalabasi","muhammedkalabasi@gmail.com","5343343434","Password_1234");
        customer = customerRepository.save(customer);
        RefreshToken refreshToken = refreshTokenService.createRefreshTokenForCustomer(customer);
        Optional<RefreshToken> savedRefreshToken = refreshTokenRepository.findById(1L);
        assertThat(savedRefreshToken).isPresent();
        assertThat(savedRefreshToken.get().getToken()).isEqualTo(refreshToken.getToken());
        assertThat(savedRefreshToken.get().getCustomer()).isEqualTo(customer);
        assertThat(savedRefreshToken.get().getExpiryDate()).isAfter(Instant.now());
    }
}
