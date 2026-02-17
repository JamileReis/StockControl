package com.stockcontrol.company.controller;

import com.stockcontrol.company.dto.ProductionSuggestionDTO;
import com.stockcontrol.company.service.ProductionService;
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
