package com.cg.repository;

import com.cg.model.Product;
import com.cg.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, String> {

    Optional<ProductImage> findByProduct(Product product);
}
