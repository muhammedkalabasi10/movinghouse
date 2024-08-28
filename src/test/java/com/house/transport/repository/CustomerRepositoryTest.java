package com.house.transport.repository;

import com.house.transport.config.TestApplication;

import com.house.transport.model.Customer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = TestApplication.class)
//@Sql("/externalprovider/repository/domain.sql")
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;
    @Test
    public void createCustomerSuccess() {
        Customer customer = new Customer(1L,"doga","yıldız","dogayildiz@outlook.com","05354463435","123.Dg_d");

        customerRepository.save(customer);

       // Optional<Customer> foundCustomer = customerRepository.findByEmail("jane.doe@example.com");

       // assertTrue(foundCustomer.isPresent());
        //assertEquals("doğa", foundCustomer.get().getName());
      //  assertEquals("Doe", foundCustomer.get().getSurname());
    }

}
