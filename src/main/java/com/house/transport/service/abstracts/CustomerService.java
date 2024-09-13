package com.house.transport.service.abstracts;

import com.house.transport.model.Customer;
import com.house.transport.model.Mover;

import java.util.List;

public interface CustomerService {
    public abstract List<Customer> getCustomers();
    public abstract Customer getCustomerById(Long id);
    public abstract Customer updateCustomer(Customer customer);
    void deleteCustomerById(Long id);
}
