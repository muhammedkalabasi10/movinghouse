package com.house.transport.security.detailsservice;

import com.house.transport.model.Mover;
import com.house.transport.repository.MoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service

public class MoverDetailsService implements UserDetailsService {

    @Autowired
    private MoverRepository moverRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Mover mover = moverRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("Mover not found"));
        return User.builder()
                .username(mover.getEmail())
                .password(mover.getPassword())
                .roles("MOVER")
                .build();
    }
}
