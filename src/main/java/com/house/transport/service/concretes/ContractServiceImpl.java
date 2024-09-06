package com.house.transport.service.concretes;


import com.house.transport.model.Contract;
import com.house.transport.repository.ContractRepository;
import com.house.transport.service.abstracts.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;

    @Autowired
    public ContractServiceImpl(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Override
    public Contract saveContract(Contract contract) {
        return contractRepository.save(contract);
    }

    @Override
    public Optional<Contract> getContractById(Long id) {
        return contractRepository.findById(id);
    }

    @Override
    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    @Override
    public void deleteContractById(Long id) {
        contractRepository.deleteById(id);
    }

    @Override
    public List<Contract> getContractsByCustomerId(Long customerId) {
        return contractRepository.findByCustomerId(customerId);
    }


}