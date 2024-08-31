package com.house.transport.repository;

import com.house.transport.config.TestApplication;
import com.house.transport.model.Customer;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = TestApplication.class)
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

    @Test
    public void fetchCustomersSuccess(){
        List<Customer> customerList = new ArrayList<>();
        for(int i=0;i<5;i++){
            customerList.add(new Customer(null, "TestName"+i,"TestSurname"+i,"testmail"+i+"@gmail.com",String.valueOf(i).repeat(10),"PasswordText*123"));
        }
        customerRepository.saveAllAndFlush(customerList);
        List<Customer> fetchedCustomerList = customerRepository.findAll();
        int testCustomerIndex = 0;
        assertThat(fetchedCustomerList)
                .extracting(Customer::getName, Customer::getSurname, Customer::getEmail)
                .contains(new Tuple(
                                customerList.get(testCustomerIndex).getName(),
                                customerList.get(testCustomerIndex).getSurname(),
                                customerList.get(testCustomerIndex).getEmail())
                );
    }

    @Test
    public void findCustomerByIdSuccess() {
        Customer customer = new Customer(null, "john", "doe", "johndoe@example.com", "1234567890", "123.Dg_d");
        Customer createdCustomer = customerRepository.save(customer);

        Optional<Customer> foundCustomer = customerRepository.findById(createdCustomer.getId());
        assertThat(foundCustomer).isPresent()
                .get()
                .extracting(Customer::getEmail, Customer::getPhone)
                .containsExactly(customer.getEmail(), customer.getPhone());
    }
    @Test
    public void updateCustomerSuccess() {
        Customer customer = new Customer(null, "jane", "doe", "janedoe@example.com", "0987654321", "123.Dg_d");
        Customer createdCustomer = customerRepository.save(customer);

        createdCustomer.setPhone("1111111111");
        Customer updatedCustomer = customerRepository.save(createdCustomer);

        assertThat(updatedCustomer)
                .extracting(Customer::getPhone)
                .isEqualTo("1111111111");
    }

    @Test
    public void deleteCustomerSuccess() {
        Customer customer = new Customer(null, "alex", "smith", "alexsmith@example.com", "5555555555", "123.Dg_d");
        Customer createdCustomer = customerRepository.save(customer);

        customerRepository.delete(createdCustomer);
        Optional<Customer> deletedCustomer = customerRepository.findById(createdCustomer.getId());

        assertThat(deletedCustomer).isNotPresent();
    }

}
