package com.house.transport.controller;

import com.house.transport.model.Mover;
import com.house.transport.service.abstracts.MoverService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/page")
    public ResponseEntity<List<Mover>> getMoverList(@RequestParam int page_num, @RequestParam int record_num){
        return ResponseEntity.ok(moverService.getMoverList(page_num, record_num));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mover> getMoverById(@PathVariable Long id){
        Mover mover = moverService.getMoverById(id);
        return ResponseEntity.ok(mover);

    }

    @PutMapping
    public ResponseEntity<Mover> updateMover(@RequestBody @Valid Mover mover){
        return ResponseEntity.ok(moverService.updateMover(mover));
    }
}
