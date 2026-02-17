package com.stockcontrol.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionSuggestionDTO {
    private List<ProductionItem> suggestedProductions = new ArrayList<>();
    private BigDecimal totalValue;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductionItem {
        private Long productId;
        private String productName;
        private BigDecimal productValue;
        private BigDecimal quantityToProduce;
        private BigDecimal totalValueForProduct;
    }
}
