package com.house.transport.repository;

import com.house.transport.config.TestApplication;
import com.house.transport.model.Contract;
import com.house.transport.model.Customer;
import com.house.transport.repository.ContractRepository;
import com.house.transport.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = TestApplication.class)
public class ContractRepositoryTest {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private CustomerRepository customerRepository; // Customer verilerini eklemek için

    @Test
    public void createContractSuccess() {
        // Önce bir müşteri kaydediyoruz
        Customer customer = new Customer(null, "doga", "yıldız", "dogayildiz@outlook.com", "5354463435", "123.Dg_d");
        customerRepository.save(customer);

        // Yeni bir sözleşme oluşturuyoruz
        Contract contract = new Contract(
                null,
                "İstanbul",
                "Ankara",
                LocalDate.now().plusDays(1),
                "house",
                3,
                2,
                customer,
                101L,          // moverId
                1500.0,        // totalPrice
                "Pending"      // status
        );
        Contract createdContract = contractRepository.save(contract);

        // Sözleşmenin başarıyla kaydedildiğini doğruluyoruz
        assertThat(createdContract)
                .extracting(
                        Contract::getLoadingCity,
                        Contract::getUnloadingCity,
                        Contract::getDate,
                        Contract::getPropertyType,
                        Contract::getBedroomNum,
                        Contract::getFloorNum,
                        Contract::getMoverId,
                        Contract::getTotalPrice,
                        Contract::getStatus
                )
                .containsExactly(
                        contract.getLoadingCity(),
                        contract.getUnloadingCity(),
                        contract.getDate(),
                        contract.getPropertyType(),
                        contract.getBedroomNum(),
                        contract.getFloorNum(),
                        contract.getMoverId(),
                        contract.getTotalPrice(),
                        contract.getStatus()
                );
    }

    @Test
    public void findContractByIdSuccess() {
        // Önce bir müşteri kaydediyoruz
        Customer customer = new Customer(null, "john", "doe", "johndoe@example.com", "1234567890", "123.Dg_d");
        customerRepository.save(customer);

        // Yeni bir sözleşme oluşturuyoruz
        Contract contract = new Contract(
                null,
                "İstanbul",
                "Bursa",
                LocalDate.now().plusDays(5),
                "apartment",
                2,
                1,
                customer,
                102L,          // moverId
                2000.0,        // totalPrice
                "Confirmed"    // status
        );
        Contract createdContract = contractRepository.save(contract);

        // Sözleşmeyi ID ile buluyoruz
        Optional<Contract> foundContract = contractRepository.findById(createdContract.getId());
        assertThat(foundContract).isPresent()
                .get()
                .extracting(
                        Contract::getLoadingCity,
                        Contract::getUnloadingCity,
                        Contract::getDate,
                        Contract::getPropertyType,
                        Contract::getBedroomNum,
                        Contract::getFloorNum,
                        Contract::getMoverId,
                        Contract::getTotalPrice,
                        Contract::getStatus
                )
                .containsExactly(
                        contract.getLoadingCity(),
                        contract.getUnloadingCity(),
                        contract.getDate(),
                        contract.getPropertyType(),
                        contract.getBedroomNum(),
                        contract.getFloorNum(),
                        contract.getMoverId(),
                        contract.getTotalPrice(),
                        contract.getStatus()
                );
    }

    @Test
    public void updateContractSuccess() {
        // Önce bir müşteri kaydediyoruz
        Customer customer = new Customer(null, "alex", "smith", "alexsmith@example.com", "5555555555", "123.Dg_d");
        customerRepository.save(customer);

        // Yeni bir sözleşme oluşturuyoruz
        Contract contract = new Contract(
                null,
                "İstanbul",
                "Adana",
                LocalDate.now().plusDays(2),
                "studio",
                1,
                3,
                customer,
                103L,          // moverId
                1200.0,        // totalPrice
                "In Progress"  // status
        );
        Contract createdContract = contractRepository.save(contract);

        // Sözleşmeyi güncelliyoruz
        createdContract.setLoadingCity("İzmir");
        createdContract.setTotalPrice(1300.0); // updated totalPrice
        Contract updatedContract = contractRepository.save(createdContract);

        // Güncellenmiş sözleşmenin doğru olduğunu doğruluyoruz
        assertThat(updatedContract)
                .extracting(
                        Contract::getLoadingCity,
                        Contract::getTotalPrice
                )
                .containsExactly("İzmir", 1300.0);
    }

    @Test
    public void deleteContractSuccess() {
        // Önce bir müşteri kaydediyoruz
        Customer customer = new Customer(null, "mary", "jones", "maryjones@example.com", "6666666666", "123.Dg_d");
        customerRepository.save(customer);

        // Yeni bir sözleşme oluşturuyoruz
        Contract contract = new Contract(
                null,
                "Ankara",
                "İzmir",
                LocalDate.now().plusDays(3),
                "house",
                4,
                2,
                customer,
                104L,          // moverId
                2500.0,        // totalPrice
                "Cancelled"    // status
        );
        Contract createdContract = contractRepository.save(contract);

        // Sözleşmeyi siliyoruz
        contractRepository.delete(createdContract);

        // Sözleşmenin silindiğini doğruluyoruz
        Optional<Contract> deletedContract = contractRepository.findById(createdContract.getId());
        assertThat(deletedContract).isNotPresent();
    }
}