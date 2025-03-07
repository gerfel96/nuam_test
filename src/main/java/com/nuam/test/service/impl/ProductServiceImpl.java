package com.nuam.test.service.impl;

import com.nuam.test.entity.ProductEntity;
import com.nuam.test.repository.ProductRepository;
import com.nuam.test.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<ProductEntity> getAllProducts(int page, int size) throws Exception {
        try{
            Pageable pageable = PageRequest.of(page, size);
            return productRepository.findAll(pageable);
        } catch (Exception e){
            throw new Exception("Error getting products");
        }
    }

    @Override
    public ProductEntity getProductById(Integer id) throws Exception {
        try{
            ProductEntity product = productRepository.findById(id).orElseThrow(() -> new Exception("Product not found"));
            return product;
        } catch (Exception e){
            throw new Exception("Error getting product");
        }
    }

    @Override
    public ProductEntity createProduct(ProductEntity product) throws Exception {
        try{
            ProductEntity newProduct = productRepository.save(product);
            return newProduct;
        } catch (Exception e){
            throw new Exception("Error creating product");
        }
    }

    @Override
    public ProductEntity updateProduct(Integer id, ProductEntity product) throws Exception {
        try{
            ProductEntity productToUpdate = productRepository.findById(id).orElseThrow(() -> new Exception("Product not found"));
            productToUpdate.setName(product.getName());
            productToUpdate.setDescription(product.getDescription());
            productToUpdate.setPrice(product.getPrice());
            ProductEntity updatedProduct = productRepository.save(productToUpdate);
            return updatedProduct;
        } catch (Exception e){
            throw new Exception("Error updating product");
        }
    }

    @Override
    public void deleteProduct(Integer id) throws Exception {
        try{
            productRepository.deleteById(id);
        } catch (Exception e){
            throw new Exception("Error deleting product");
        }
    }
}
