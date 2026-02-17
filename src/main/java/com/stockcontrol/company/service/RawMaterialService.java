package com.stockcontrol.company.service;

import com.stockcontrol.company.dto.RawMaterialDTO;
import com.stockcontrol.company.exception.ResourceNotFoundException;
import com.stockcontrol.company.model.RawMaterial;
import com.stockcontrol.company.repository.RawMaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RawMaterialService {

    private final RawMaterialRepository rawMaterialRepository;

    public List<RawMaterialDTO> findAll() {
        return rawMaterialRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public RawMaterialDTO findById(Long id) {
        RawMaterial rawMaterial = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Raw material not found: " + id));
        return toDTO(rawMaterial);
    }

    public RawMaterialDTO create(RawMaterialDTO dto) {
        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setName(dto.getName());
        rawMaterial.setStockQuantity(dto.getStockQuantity());
        RawMaterial saved = rawMaterialRepository.save(rawMaterial);
        return toDTO(saved);
    }

    public RawMaterialDTO update(Long id, RawMaterialDTO dto) {
        RawMaterial rawMaterial = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Raw material not found: " + id));
        rawMaterial.setName(dto.getName());
        rawMaterial.setStockQuantity(dto.getStockQuantity());
        RawMaterial updated = rawMaterialRepository.save(rawMaterial);
        return toDTO(updated);
    }

    public void delete(Long id) {
        if (!rawMaterialRepository.existsById(id)) {
            throw new ResourceNotFoundException("Raw material not found: " + id);
        }
        rawMaterialRepository.deleteById(id);
    }

    private RawMaterialDTO toDTO(RawMaterial rawMaterial) {
        return new RawMaterialDTO(rawMaterial.getId(), rawMaterial.getName(), rawMaterial.getStockQuantity());
    }
}
