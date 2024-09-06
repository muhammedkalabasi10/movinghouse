package com.house.transport.security.detailsservice;

import com.house.transport.model.Mover;
import com.house.transport.repository.MoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.AccessDeniedException;

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
    public Mover updateMover(Long id, Mover updatedMover) {
        // Fetch mover by id
        Mover mover = moverRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Mover not found"));

        // Update the mover details
        mover.setEmail(updatedMover.getEmail());
        mover.setPhone(updatedMover.getPhone());
        mover.setPassword(updatedMover.getPassword());
        mover.setCompany_name(updatedMover.getCompany_name());
        mover.setLogo(updatedMover.getLogo());
        mover.setAbout(updatedMover.getAbout());
        mover.setLicences_information(updatedMover.getLicences_information());
        mover.setK1_certificate(updatedMover.getK1_certificate());
        mover.setK3_certificate(updatedMover.getK3_certificate());
        mover.setVkn(updatedMover.getVkn());
        mover.setMax_floor(updatedMover.getMax_floor());

        return moverRepository.save(mover);
    }

    // Delete Mover: only the owner can delete their account
    public void deleteMover(Long id) {
        // Fetch mover by id
        Mover mover = moverRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Mover not found"));

        // Delete the mover
        moverRepository.delete(mover);
    }
}