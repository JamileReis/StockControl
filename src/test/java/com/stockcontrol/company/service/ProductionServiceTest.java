package com.stockcontrol.company.service;

import com.stockcontrol.company.dto.ProductionSuggestionDTO;
import com.stockcontrol.company.model.Product;
import com.stockcontrol.company.model.ProductComposition;
import com.stockcontrol.company.model.RawMaterial;
import com.stockcontrol.company.repository.CompositionRepository;
import com.stockcontrol.company.repository.ProductRepository;
import com.stockcontrol.company.repository.RawMaterialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductionServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private RawMaterialRepository rawMaterialRepository;

    @Mock
    private CompositionRepository compositionRepository;

    @InjectMocks
    private ProductionService productionService;

    private Product productA;
    private Product productB;
    private RawMaterial steel;
    private RawMaterial plastic;

    @BeforeEach
    void setUp() {
        productA = new Product();
        productA.setId(1L);
        productA.setName("Product A");
        productA.setValue(BigDecimal.valueOf(200.0));

        productB = new Product();
        productB.setId(2L);
        productB.setName("Product B");
        productB.setValue(BigDecimal.valueOf(150.0));

        steel = new RawMaterial();
        steel.setId(1L);
        steel.setName("Steel");
        steel.setStockQuantity(BigDecimal.valueOf(1000.0));

        plastic = new RawMaterial();
        plastic.setId(2L);
        plastic.setName("Plastic");
        plastic.setStockQuantity(BigDecimal.valueOf(500.0));
    }

    @Test
    void shouldCalculateProductionSuggestion() {
        ProductComposition compA1 = new ProductComposition();
        compA1.setProduct(productA);
        compA1.setRawMaterial(steel);
        compA1.setQuantityRequired(BigDecimal.valueOf(10.0));

        ProductComposition compA2 = new ProductComposition();
        compA2.setProduct(productA);
        compA2.setRawMaterial(plastic);
        compA2.setQuantityRequired(BigDecimal.valueOf(5.0));

        when(productRepository.findAllOrderByValueDesc()).thenReturn(Arrays.asList(productA, productB));
        when(rawMaterialRepository.findAll()).thenReturn(Arrays.asList(steel, plastic));
        when(compositionRepository.findByProductId(1L)).thenReturn(Arrays.asList(compA1, compA2));
        when(compositionRepository.findByProductId(2L)).thenReturn(List.of());

        ProductionSuggestionDTO result = productionService.calculateProductionSuggestion();

        assertThat(result.getSuggestedProductions()).isNotEmpty();
        assertThat(result.getTotalValue()).isGreaterThan(BigDecimal.ZERO);
    }

    @Test
    void shouldReturnEmptyWhenNoCompositions() {
        when(productRepository.findAllOrderByValueDesc()).thenReturn(Arrays.asList(productA));
        when(rawMaterialRepository.findAll()).thenReturn(Arrays.asList(steel));
        when(compositionRepository.findByProductId(1L)).thenReturn(List.of());

        ProductionSuggestionDTO result = productionService.calculateProductionSuggestion();

        assertThat(result.getSuggestedProductions()).isEmpty();
        assertThat(result.getTotalValue()).isEqualByComparingTo(BigDecimal.ZERO);
    }
}
