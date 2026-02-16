package com.stockcontrol.controller;

import com.stockcontrol.dto.ProductionSuggestionDTO;
import com.stockcontrol.service.ProductionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/production")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ProductionController {

    private final ProductionService productionService;

    @GetMapping("/suggestion")
    public ResponseEntity<ProductionSuggestionDTO> getProductionSuggestion() {
        return ResponseEntity.ok(productionService.calculateProductionSuggestion());
    }
}
