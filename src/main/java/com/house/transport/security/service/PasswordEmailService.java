package com.house.transport.security.service;

import com.house.transport.model.Customer;
import com.house.transport.model.Mover;
import com.house.transport.repository.CustomerRepository;
import com.house.transport.repository.MoverRepository;
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
    MoverRepository moverRepository;
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
    public Mover changeMoverPassword(String email, String oldPassword, String newPassword){
        Mover mover = moverRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Mover not found"));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email,oldPassword)
        );
        mover.setPassword(passwordEncoder.encode(newPassword));
        return moverRepository.save(mover);
    }

    public Mover changeMoverEmail(String oldEmail, String newEmail, String password){
        Mover mover = moverRepository.findByEmail(oldEmail).orElseThrow(()-> new UsernameNotFoundException("Mover not found"));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(oldEmail,password)
        );
        mover.setEmail(newEmail);
        return moverRepository.save(mover);
    }
}
