package com.nuam.test.controller;

import com.nuam.test.entity.ProductEntity;
import com.nuam.test.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductEntity>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
       try{
           Page<ProductEntity> products = productService.getAllProducts(page, size);
           return ResponseEntity.ok(products);
         }catch (Exception e){
              return ResponseEntity.badRequest().build();
       }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable Integer id){
        try{
            ProductEntity product = productService.getProductById(id);
            System.out.println("Product: " + product.toString());
            return ResponseEntity.ok(product);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ProductEntity> createProduct(@Valid @RequestBody ProductEntity product){
        try{
            ProductEntity newProduct = productService.createProduct(product);
            return ResponseEntity.ok(newProduct);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity> updateProduct(@PathVariable Integer id,@Valid @RequestBody ProductEntity product){
        try{
            ProductEntity updatedProduct = productService.updateProduct(id, product);
            return ResponseEntity.ok(updatedProduct);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id){
        try{
            productService.deleteProduct(id);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }


}
