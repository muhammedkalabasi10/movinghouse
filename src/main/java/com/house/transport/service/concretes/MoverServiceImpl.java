package com.house.transport.service.concretes;

import com.house.transport.exception.custom.NotFoundException;
import com.house.transport.model.Customer;
import com.house.transport.model.Mover;
import com.house.transport.repository.MoverRepository;
import com.house.transport.service.abstracts.MoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoverServiceImpl implements MoverService {

    @Autowired
    MoverRepository moverRepository;

    @Override
    public List<Mover> fetchMovers() {
        return moverRepository.findAll();
    }


    @Override
    public Mover getMoverById(Long id) {
        return moverRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Mover not found with the given ID."));
    }

    @Override
    public Mover updateMover(Mover mover) {
        return moverRepository.save(mover);
    }

    @PreAuthorize("#id == authentication.principal.id")
    public void deleteMoverById(Long id) {
        moverRepository.deleteById(id);
    }
}