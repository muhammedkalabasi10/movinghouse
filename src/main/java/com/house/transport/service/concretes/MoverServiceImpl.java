package com.house.transport.service.concretes;

import com.house.transport.model.Mover;
import com.house.transport.repository.MoverRepository;
import com.house.transport.service.abstracts.MoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoverServiceImpl implements MoverService {

    @Autowired
    MoverRepository moverRepository;

    @Override
    public Mover createMover(Mover mover) {
        return moverRepository.save(mover);
    }

    @Override
    public List<Mover> fetchMovers() {
        return moverRepository.findAll();
    }

    @Override
    public Mover getMoverById(Long id) {
        return moverRepository.findById(id).orElse(null);
    }

    @Override
    public Mover updateMover(Mover mover) {
        return moverRepository.save(mover);
    }

    @Override
    public boolean deleteMoverById(Long id) {
        if(moverRepository.existsById(id)){
            moverRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }
}
