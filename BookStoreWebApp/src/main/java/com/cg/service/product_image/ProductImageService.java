package com.cg.service.product_image;


import com.cg.model.ProductImage;

public interface ProductImageService {

    Iterable<ProductImage> findAll();

    ProductImage create(ProductImage productImage);

    void delete(ProductImage productImage);
}
