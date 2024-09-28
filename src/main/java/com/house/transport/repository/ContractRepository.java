package com.house.transport.repository;

import com.house.transport.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    List<Contract> findByCustomerId(Long customerId);

    List<Contract> findByMoverId(Long moverId);

    List<Contract> findByStatus(String status);

    List<Contract> findByPropertyType(String propertyType);

    List<Contract> findByDate(LocalDate date);
}

