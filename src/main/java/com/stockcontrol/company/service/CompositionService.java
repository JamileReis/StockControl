package com.stockcontrol.company.service;

import com.stockcontrol.company.dto.CompositionDTO;
import com.stockcontrol.company.exception.ResourceNotFoundException;
import com.stockcontrol.company.model.Product;
import com.stockcontrol.company.model.ProductComposition;
import com.stockcontrol.company.model.RawMaterial;
import com.stockcontrol.company.repository.CompositionRepository;
import com.stockcontrol.company.repository.ProductRepository;
import com.stockcontrol.company.repository.RawMaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CompositionService {

    private final CompositionRepository compositionRepository;
    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;

    public List<CompositionDTO> findAll() {
        return compositionRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public CompositionDTO findById(Long id) {
        ProductComposition composition = compositionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Composition not found: " + id));
        return toDTO(composition);
    }

    public List<CompositionDTO> findByProductId(Long productId) {
        return compositionRepository.findByProductId(productId).stream()
                .map(this::toDTO)
                .toList();
    }

    public List<CompositionDTO> findByRawMaterialId(Long rawMaterialId) {
        return compositionRepository.findByRawMaterialId(rawMaterialId).stream()
                .map(this::toDTO)
                .toList();
    }

    public CompositionDTO create(CompositionDTO dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + dto.getProductId()));
        
        RawMaterial rawMaterial = rawMaterialRepository.findById(dto.getRawMaterialId())
                .orElseThrow(() -> new ResourceNotFoundException("Raw material not found: " + dto.getRawMaterialId()));
        
        ProductComposition composition = new ProductComposition();
        composition.setProduct(product);
        composition.setRawMaterial(rawMaterial);
        composition.setQuantityRequired(dto.getQuantityRequired());
        
        ProductComposition saved = compositionRepository.save(composition);
        return toDTO(saved);
    }

    public CompositionDTO update(Long id, CompositionDTO dto) {
        ProductComposition composition = compositionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Composition not found: " + id));
        
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + dto.getProductId()));
        
        RawMaterial rawMaterial = rawMaterialRepository.findById(dto.getRawMaterialId())
                .orElseThrow(() -> new ResourceNotFoundException("Raw material not found: " + dto.getRawMaterialId()));
        
        composition.setProduct(product);
        composition.setRawMaterial(rawMaterial);
        composition.setQuantityRequired(dto.getQuantityRequired());
        
        ProductComposition updated = compositionRepository.save(composition);
        return toDTO(updated);
    }

    public void delete(Long id) {
        if (!compositionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Composition not found: " + id);
        }
        compositionRepository.deleteById(id);
    }

    private CompositionDTO toDTO(ProductComposition composition) {
        CompositionDTO dto = new CompositionDTO();
        dto.setId(composition.getId());
        dto.setProductId(composition.getProduct().getId());
        dto.setRawMaterialId(composition.getRawMaterial().getId());
        dto.setQuantityRequired(composition.getQuantityRequired());
        dto.setProductName(composition.getProduct().getName());
        dto.setRawMaterialName(composition.getRawMaterial().getName());
        return dto;
    }
}
