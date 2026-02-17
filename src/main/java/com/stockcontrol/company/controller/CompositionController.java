package com.stockcontrol.company.controller;

import com.stockcontrol.company.dto.CompositionDTO;
import com.stockcontrol.company.service.CompositionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compositions")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CompositionController {

    private final CompositionService compositionService;

    @GetMapping
    public ResponseEntity<List<CompositionDTO>> findAll() {
        return ResponseEntity.ok(compositionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompositionDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(compositionService.findById(id));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<CompositionDTO>> findByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(compositionService.findByProductId(productId));
    }

    @GetMapping("/raw-material/{rawMaterialId}")
    public ResponseEntity<List<CompositionDTO>> findByRawMaterialId(@PathVariable Long rawMaterialId) {
        return ResponseEntity.ok(compositionService.findByRawMaterialId(rawMaterialId));
    }

    @PostMapping
    public ResponseEntity<CompositionDTO> create(@Valid @RequestBody CompositionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(compositionService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompositionDTO> update(@PathVariable Long id, @Valid @RequestBody CompositionDTO dto) {
        return ResponseEntity.ok(compositionService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        compositionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
