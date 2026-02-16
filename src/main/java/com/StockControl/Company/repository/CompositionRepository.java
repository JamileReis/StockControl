package com.stockcontrol.repository;

import com.stockcontrol.model.ProductComposition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompositionRepository extends JpaRepository<ProductComposition, Long> {
    
    @Query("SELECT pc FROM ProductComposition pc WHERE pc.product.id = :productId")
    List<ProductComposition> findByProductId(Long productId);
    
    @Query("SELECT pc FROM ProductComposition pc WHERE pc.rawMaterial.id = :rawMaterialId")
    List<ProductComposition> findByRawMaterialId(Long rawMaterialId);
}
