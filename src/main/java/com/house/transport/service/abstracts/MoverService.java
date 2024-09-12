package com.house.transport.service.abstracts;

import com.house.transport.model.Mover;

import java.util.List;

public interface MoverService {
    public abstract List<Mover> fetchMovers();
    public abstract Mover getMoverById(Long id);
    public abstract Mover updateMover(Mover mover);
}
