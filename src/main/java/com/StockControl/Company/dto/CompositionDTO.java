package com.stockcontrol.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompositionDTO {
    private Long id;
    
    @NotNull
    private Long productId;
    
    @NotNull
    private Long rawMaterialId;
    
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal quantityRequired;
    
    private String productName;
    private String rawMaterialName;
}
