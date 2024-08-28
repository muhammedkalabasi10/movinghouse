package com.house.transport.repository;

import com.house.transport.config.TestApplication;
import com.house.transport.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.assertj.core.api.Assertions.assertThat;

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
        Customer customer = new Customer(null,"doga","yıldız","dogayildiz@outlook.com","5354463435","123.Dg_d");

        Customer createdCustomer = customerRepository.save(customer);
        assertThat(createdCustomer)
                .extracting(Customer::getEmail, Customer::getPhone)
                .containsExactly(customer.getEmail(), customer.getPhone());

    }

}
