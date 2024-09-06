package com.house.transport.security.detailsservice;

import com.house.transport.model.Customer;
import com.house.transport.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;

@Service
public class CustomerDetailsService implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("Customer not found"));
        return User.builder()
                .username(customer.getEmail())
                .password(customer.getPassword())
                .roles("CUSTOMER")
                .build();
    }
    // Update Customer: only the authenticated customer can update their information
    public Customer updateCustomer(Long id, Customer updatedCustomer, Authentication authentication) {
        // Fetch the customer by id
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Customer not found"));

        // Check if the authenticated user is the owner of the customer data
        if (!customer.getEmail().equals(authentication.getName())) {
            throw new AccessDeniedException("You are not authorized to update this customer");
        }

        // Update customer details
        customer.setEmail(updatedCustomer.getEmail());
        customer.setPhone(updatedCustomer.getPhone());
        customer.setPassword(updatedCustomer.getPassword());
        customer.setName(updatedCustomer.getName());
        customer.setSurname(updatedCustomer.getSurname());

        return customerRepository.save(customer);
    }

    // Delete Customer: only the authenticated customer can delete their account
    public void deleteCustomer(Long id, Authentication authentication) {
        // Fetch the customer by id
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Customer not found"));

        // Check if the authenticated user is the owner of the customer data
        if (!customer.getEmail().equals(authentication.getName())) {
            throw new AccessDeniedException("You are not authorized to delete this customer");
        }

        // Delete the customer
        customerRepository.delete(customer);
    }
}

