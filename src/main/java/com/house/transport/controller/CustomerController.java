package com.house.transport.controller;

import com.house.transport.model.Customer;
import com.house.transport.model.Mover;
import com.house.transport.service.abstracts.CustomerService;
import com.house.transport.service.abstracts.MoverService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers(){
        return ResponseEntity.ok(customerService.getCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getMoverById(@PathVariable Long id){
        Customer customer = customerService.getCustomerById(id);
        if(customer != null)
            return ResponseEntity.ok(customer);
        else
            return null; //NotFoundException exception will be create
    }

    @PutMapping
    public ResponseEntity<Customer> customerMover(@RequestBody @Valid Customer customer){
        return ResponseEntity.ok(customerService.updateCustomer(customer));
    }

    @PreAuthorize("#id == authentication.principal.id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.ok("Customer successfully deleted.");
    }

}
