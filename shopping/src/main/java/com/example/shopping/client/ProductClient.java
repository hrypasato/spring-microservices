package com.example.shopping.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.shopping.model.Product;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping(value = "products/{id}")
    @CircuitBreaker(name = "productCB",fallbackMethod = "getProductFallback")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id);
    

    @GetMapping(value = "products/{id}/stock")
    @CircuitBreaker(name = "productCB",fallbackMethod = "getProductFallback")
    public ResponseEntity<Product> updateStockProduct(@PathVariable("id") Long id ,@RequestParam(name = "quantity", required = true) Double quantity);

    default ResponseEntity<Product> getProductFallback(RuntimeException e ){
        // Product product=Product.builder()
        //         .id(0L)
        //         .name("none")
        //         .description("none")
        //         .price(0.0)
        //         .stock(0.0)
        //         .build();
        return ResponseEntity.ok(null);
    }
}

