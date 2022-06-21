package com.example.primermicro.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.primermicro.entity.Category;
import com.example.primermicro.entity.Product;
import com.example.primermicro.service.product.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> listAllProducts(
            @RequestParam(value = "category", required = false) Long categoryId) {
        
        List<Product> products = categoryId == null 
                                ? productService.listAllProducts() 
                                : productService.findByCategory(
                                    Category.builder()
                                            .id(categoryId)
                                            .build());
        
        return products.isEmpty() 
                ? ResponseEntity.noContent().build() 
                : ResponseEntity.ok(products);
    }

    @GetMapping(path = "/{productId}")
    public ResponseEntity<Product> getProductById(
                            @PathVariable("productId") Long productId) {
        
        Product product = productService.getProductById(productId);
        
        return product == null 
                ? ResponseEntity.noContent().build() 
                : ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> saveProduct(
                @Valid @RequestBody Product newProduct,
                BindingResult result) {

        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage(result));
        }
        
        Product product = productService.saveProduct(newProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping(path = "/{productId}")
    public ResponseEntity<Product> updateProduct(
                            @PathVariable("productId") Long productId,
                            @Valid @RequestBody Product product) {

        Product target = productService.getProductById(productId);
        if (target == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(productService.updateProduct(target));
    }

    @DeleteMapping(path = "/{productId}")
    public ResponseEntity<Void> deleteProduct(
                            @PathVariable("productId") Long productId) {
        
        Product target = productService.getProductById(productId);
        if (target == null) {
            return ResponseEntity.notFound().build();
        }

        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping(path = "/{productId}/stock")
    public ResponseEntity<Product> updateStock(
                            @PathVariable("productId") Long productId,
                            @RequestParam("cantidad") Double cantidad) {
        
        Product target = productService.getProductById(productId);
        if (target == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(productService.updateStock(productId, cantidad));
    }

    private String formatMessage(BindingResult result) {
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err -> {
                    Map<String,String> error = new HashMap();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());

        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String json = "";

        try {
            json = mapper.writeValueAsString(errorMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
}
