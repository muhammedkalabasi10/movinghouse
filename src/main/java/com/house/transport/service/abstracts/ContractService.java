package com.house.transport.service.abstracts;

import com.house.transport.model.Contract;

import java.util.List;
import java.util.Optional;

public interface ContractService {

    Contract saveContract(Contract contract);

    Contract getContractById(Long id);

    List<Contract> getAllContracts();

    void deleteContractById(Long id);

    List<Contract> getContractsByCustomerId(Long customerId);
}