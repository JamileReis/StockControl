package com.stockcontrol.service;

import com.stockcontrol.dto.RawMaterialDTO;
import com.stockcontrol.exception.ResourceNotFoundException;
import com.stockcontrol.model.RawMaterial;
import com.stockcontrol.repository.RawMaterialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RawMaterialServiceTest {

    @Mock
    private RawMaterialRepository rawMaterialRepository;

    @InjectMocks
    private RawMaterialService rawMaterialService;

    private RawMaterial rawMaterial;
    private RawMaterialDTO rawMaterialDTO;

    @BeforeEach
    void setUp() {
        rawMaterial = new RawMaterial();
        rawMaterial.setId(1L);
        rawMaterial.setName("Steel");
        rawMaterial.setStockQuantity(BigDecimal.valueOf(1000.0));

        rawMaterialDTO = new RawMaterialDTO(null, "Steel", BigDecimal.valueOf(1000.0));
    }

    @Test
    void shouldCreateRawMaterial() {
        when(rawMaterialRepository.save(any(RawMaterial.class))).thenReturn(rawMaterial);

        RawMaterialDTO result = rawMaterialService.create(rawMaterialDTO);

        assertThat(result.getName()).isEqualTo("Steel");
        assertThat(result.getStockQuantity()).isEqualByComparingTo(BigDecimal.valueOf(1000.0));
        verify(rawMaterialRepository).save(any(RawMaterial.class));
    }

    @Test
    void shouldFindRawMaterialById() {
        when(rawMaterialRepository.findById(1L)).thenReturn(Optional.of(rawMaterial));

        RawMaterialDTO result = rawMaterialService.findById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Steel");
    }

    @Test
    void shouldThrowExceptionWhenRawMaterialNotFound() {
        when(rawMaterialRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> rawMaterialService.findById(1L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
