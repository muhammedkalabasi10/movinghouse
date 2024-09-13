package com.house.transport.controller;

import com.house.transport.model.Mover;
import com.house.transport.service.abstracts.MoverService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movers")
public class MoverController {
    @Autowired
    private MoverService moverService;

    @GetMapping
    public ResponseEntity<List<Mover>> fetchMovers(){
        return ResponseEntity.ok(moverService.fetchMovers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mover> getMoverById(@PathVariable Long id){
        Mover mover = moverService.getMoverById(id);
        if(mover != null)
            return ResponseEntity.ok(mover);
        else
            return null; //NotFoundException exception will be create
    }

    @PutMapping
    public ResponseEntity<Mover> updateMover(@RequestBody @Valid Mover mover){
        return ResponseEntity.ok(moverService.updateMover(mover));
    }
    @PreAuthorize("#id == authentication.principal.id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMover(@PathVariable Long id) {
        moverService.deleteMoverById(id);
        return ResponseEntity.ok("Mover successfully deleted.");
    }
}
