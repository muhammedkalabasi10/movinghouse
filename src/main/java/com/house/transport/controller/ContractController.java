package com.house.transport.controller;

import com.house.transport.model.Contract;
import com.house.transport.service.abstracts.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    @Autowired
    private ContractService contractService;

    // Create a new contract
    @PostMapping
    public ResponseEntity<Contract> createContract(@RequestBody Contract contract) {
        Contract savedContract = contractService.saveContract(contract);
        return ResponseEntity.ok(savedContract);
    }

    // Get a contract by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Contract> getContractById(@PathVariable Long id) {
        Optional<Contract> contract = contractService.getContractById(id);
        return contract.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get all contracts
    @GetMapping
    public ResponseEntity<List<Contract>> getAllContracts() {
        List<Contract> contracts = contractService.getAllContracts();
        return ResponseEntity.ok(contracts);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable Long id) {
        contractService.deleteContractById(id);
        return ResponseEntity.noContent().build();
    }

    // Get contracts by customer ID
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Contract>> getContractsByCustomerId(@PathVariable Long customerId) {
        List<Contract> contracts = contractService.getContractsByCustomerId(customerId);
        return ResponseEntity.ok(contracts);
    }
}
