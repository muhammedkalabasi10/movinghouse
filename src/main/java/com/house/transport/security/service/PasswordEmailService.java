package com.house.transport.security.service;

import com.house.transport.model.Customer;
import com.house.transport.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEmailService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    public Customer changeCustomerPassword(String email, String oldPassword, String newPassword) {
        Customer customer = customerRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Customer not found"));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, oldPassword)
        );
        // Update customer details
        customer.setPassword(passwordEncoder.encode(newPassword));
        return customerRepository.save(customer);
    }
}
