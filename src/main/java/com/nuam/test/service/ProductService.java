package com.nuam.test.service;

import com.nuam.test.entity.ProductEntity;
import org.springframework.data.domain.Page;

public interface ProductService {

    public Page<ProductEntity> getAllProducts(int page, int size) throws Exception;
    public ProductEntity getProductById(Integer id) throws Exception;

    public ProductEntity createProduct(ProductEntity product) throws Exception;

    public ProductEntity updateProduct(Integer id, ProductEntity product) throws Exception;

    public void deleteProduct(Integer id) throws Exception;
}
