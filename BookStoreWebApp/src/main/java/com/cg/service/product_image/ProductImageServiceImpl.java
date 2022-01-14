package com.cg.service.product_image;


import com.cg.model.ProductImage;
import com.cg.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Override
    public Iterable<ProductImage> findAll() {
        return productImageRepository.findAll();
    }

    @Override
    public ProductImage create(ProductImage productImage) {
        return productImageRepository.save(productImage);
    }

    @Override
    public void delete(ProductImage productMedia) {
        productImageRepository.delete(productMedia);
    }
}
