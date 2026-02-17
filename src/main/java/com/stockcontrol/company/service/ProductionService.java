package com.stockcontrol.company.service;

import com.stockcontrol.company.dto.ProductionSuggestionDTO;
import com.stockcontrol.company.model.Product;
import com.stockcontrol.company.model.ProductComposition;
import com.stockcontrol.company.model.RawMaterial;
import com.stockcontrol.company.repository.CompositionRepository;
import com.stockcontrol.company.repository.ProductRepository;
import com.stockcontrol.company.repository.RawMaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductionService {

    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;
    private final CompositionRepository compositionRepository;

    public ProductionSuggestionDTO calculateProductionSuggestion() {
        List<Product> products = productRepository.findAllOrderByValueDesc();
        Map<Long, BigDecimal> availableStock = createAvailableStockMap();
        
        ProductionSuggestionDTO suggestion = new ProductionSuggestionDTO();
        BigDecimal totalValue = BigDecimal.ZERO;
        
        for (Product product : products) {
            BigDecimal maxQuantity = calculateMaxProductionQuantity(product, availableStock);
            
            if (maxQuantity.compareTo(BigDecimal.ZERO) > 0) {
                updateAvailableStock(product, maxQuantity, availableStock);
                
                BigDecimal productTotalValue = product.getValue().multiply(maxQuantity);
                totalValue = totalValue.add(productTotalValue);
                
                ProductionSuggestionDTO.ProductionItem item = new ProductionSuggestionDTO.ProductionItem();
                item.setProductId(product.getId());
                item.setProductName(product.getName());
                item.setProductValue(product.getValue());
                item.setQuantityToProduce(maxQuantity);
                item.setTotalValueForProduct(productTotalValue);
                
                suggestion.getSuggestedProductions().add(item);
            }
        }
        
        suggestion.setTotalValue(totalValue);
        return suggestion;
    }
    
    private Map<Long, BigDecimal> createAvailableStockMap() {
        Map<Long, BigDecimal> stockMap = new HashMap<>();
        List<RawMaterial> rawMaterials = rawMaterialRepository.findAll();
        
        for (RawMaterial rm : rawMaterials) {
            stockMap.put(rm.getId(), rm.getStockQuantity());
        }
        
        return stockMap;
    }
    
    private BigDecimal calculateMaxProductionQuantity(Product product, Map<Long, BigDecimal> availableStock) {
        List<ProductComposition> compositions = compositionRepository.findByProductId(product.getId());
        
        if (compositions.isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal maxQuantity = null;
        
        for (ProductComposition composition : compositions) {
            Long rawMaterialId = composition.getRawMaterial().getId();
            BigDecimal available = availableStock.getOrDefault(rawMaterialId, BigDecimal.ZERO);
            BigDecimal required = composition.getQuantityRequired();
            
            BigDecimal possibleQuantity = available.divide(required, 2, RoundingMode.DOWN);
            
            if (maxQuantity == null || possibleQuantity.compareTo(maxQuantity) < 0) {
                maxQuantity = possibleQuantity;
            }
        }
        
        return maxQuantity != null ? maxQuantity : BigDecimal.ZERO;
    }
    
    private void updateAvailableStock(Product product, BigDecimal quantity, Map<Long, BigDecimal> availableStock) {
        List<ProductComposition> compositions = compositionRepository.findByProductId(product.getId());
        
        for (ProductComposition composition : compositions) {
            Long rawMaterialId = composition.getRawMaterial().getId();
            BigDecimal consumed = composition.getQuantityRequired().multiply(quantity);
            BigDecimal remaining = availableStock.get(rawMaterialId).subtract(consumed);
            availableStock.put(rawMaterialId, remaining);
        }
    }
}
