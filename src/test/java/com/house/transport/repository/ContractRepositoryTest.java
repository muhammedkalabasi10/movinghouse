package com.house.transport.repository;

import com.house.transport.config.TestApplication;
import com.house.transport.model.Contract;
import com.house.transport.model.Customer;
import jakarta.transaction.Transactional;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = TestApplication.class)
@Transactional
public class ContractRepositoryTest {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void createContractSuccess() {
        Customer customer = new Customer(null, "doga", "yıldız", "dogayildiz@outlook.com", "5354463435", "123.Dg_d");
        customerRepository.save(customer);

        Contract contract = new Contract(
                null,
                "İstanbul",
                "Ankara",
                LocalDate.now().plusDays(1),
                "house",
                3,
                2,
                customer,
                101L,
                1500.0,
                "Pending"
        );
        Contract createdContract = contractRepository.save(contract);

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
    public void fetchContractssSuccess(){
        List<Customer> customerList = new ArrayList<>();
        int listSize = 3;
        for(int i=0;i<listSize;i++){
            customerList.add(new Customer(null, "TestName"+i,"TestSurname"+i,"testmail"+i+"@gmail.com",String.valueOf(i).repeat(10),"PasswordText*123"));
        }
        customerRepository.saveAllAndFlush(customerList);
        List<Contract> contractList = new ArrayList<>();
        for(int i=0;i<listSize;i++){
            contractList.add(new Contract(
                    null,
                    "TestLoadingCity"+i,
                    "TestUnloadingCity"+i,
                    LocalDate.now().plusDays(i),
                    "TestApartment"+i,
                    1,
                    1,
                    customerList.get(i),
                    (long) i,
                    i*1000,
                    "Confirmed"
            ));
        }
        contractRepository.saveAllAndFlush(contractList);

        int testContractIndex = 0;

        List<Contract> fetchedContractList = contractRepository.findAll();
        assertThat(fetchedContractList)
                .hasSize(listSize)
                .extracting(
                        Contract::getLoadingCity,
                        Contract::getUnloadingCity,
                        Contract::getDate
                ).contains(new Tuple(
                        contractList.get(testContractIndex).getLoadingCity(),
                        contractList.get(testContractIndex).getUnloadingCity(),
                        contractList.get(testContractIndex).getDate())
                );
    }

    @Test
    public void findContractByIdSuccess() {
        Customer customer = new Customer(null, "john", "doe", "johndoe@example.com", "1234567890", "123.Dg_d");
        customerRepository.save(customer);

        Contract contract = new Contract(
                null,
                "İstanbul",
                "Bursa",
                LocalDate.now().plusDays(5),
                "apartment",
                2,
                1,
                customer,
                102L,
                2000.0,
                "Confirmed"
        );
        Contract createdContract = contractRepository.save(contract);

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
        Customer customer = new Customer(null, "alex", "smith", "alexsmith@example.com", "5555555555", "123.Dg_d");
        customerRepository.save(customer);

        Contract contract = new Contract(
                null,
                "İstanbul",
                "Adana",
                LocalDate.now().plusDays(2),
                "studio",
                1,
                3,
                customer,
                103L,
                1200.0,
                "In Progress"
        );
        Contract createdContract = contractRepository.save(contract);

        createdContract.setLoadingCity("İzmir");
        createdContract.setTotalPrice(1300.0);
        Contract updatedContract = contractRepository.save(createdContract);

        assertThat(updatedContract)
                .extracting(
                        Contract::getLoadingCity,
                        Contract::getTotalPrice
                )
                .containsExactly("İzmir", 1300.0);
    }

    @Test
    public void deleteContractSuccess() {
        Customer customer = new Customer(null, "mary", "jones", "maryjones@example.com", "6666666666", "123.Dg_d");
        customerRepository.save(customer);

        Contract contract = new Contract(
                null,
                "Ankara",
                "İzmir",
                LocalDate.now().plusDays(3),
                "house",
                4,
                2,
                customer,
                104L,
                2500.0,
                "Cancelled"
        );
        Contract createdContract = contractRepository.save(contract);

        contractRepository.delete(createdContract);

        Optional<Contract> deletedContract = contractRepository.findById(createdContract.getId());
        assertThat(deletedContract).isNotPresent();
    }
}