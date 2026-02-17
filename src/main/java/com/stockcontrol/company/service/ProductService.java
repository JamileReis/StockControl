package com.stockcontrol.company.service;

import com.stockcontrol.company.dto.ProductDTO;
import com.stockcontrol.company.exception.ResourceNotFoundException;
import com.stockcontrol.company.model.Product;
import com.stockcontrol.company.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public ProductDTO findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        return toDTO(product);
    }

    public ProductDTO create(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setValue(dto.getValue());
        Product saved = productRepository.save(product);
        return toDTO(saved);
    }

    public ProductDTO update(Long id, ProductDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        product.setName(dto.getName());
        product.setValue(dto.getValue());
        Product updated = productRepository.save(product);
        return toDTO(updated);
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found: " + id);
        }
        productRepository.deleteById(id);
    }

    private ProductDTO toDTO(Product product) {
        return new ProductDTO(product.getId(), product.getName(), product.getValue());
    }
}
