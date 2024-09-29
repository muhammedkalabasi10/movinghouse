package com.house.transport.service.abstracts;

import com.house.transport.model.Mover;

import java.util.List;

public interface MoverService {
    List<Mover> fetchMovers();
    List<Mover> getMoverList(int page_num, int record_num);
    Mover getMoverById(Long id);
    Mover updateMover(Mover mover);
    void deleteMoverById(Long id);
}
