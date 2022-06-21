package com.example.primermicro.service.product;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.primermicro.entity.Category;
import com.example.primermicro.entity.Product;
import com.example.primermicro.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImplements  implements ProductService {
    
    private final ProductRepository productRepository;

    @Override
    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product saveProduct(Product product) {
        product.setStatus("CREATED");
        product.setCreateAt(new Date());
        
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product target = getProductById(product.getId());
        return productRepository.save(updateProduct(target, product));

    }

    @Override
    public void deleteProduct(Long id) {
        Product target = getProductById(id);
        target.setStatus("DELETED");
        productRepository.save(target);
        
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Product updateStock(Long id, Double cantidad) {
        Product target = getProductById(id);
        target.setStock(target.getStock() + cantidad);
        return productRepository.save(target);
    }
    
    //update all fields of the product
    private Product updateProduct(Product currentProduct, Product product) {
        currentProduct.setName(product.getName());
        currentProduct.setDescription(product.getDescription());
        currentProduct.setPrice(product.getPrice());
        currentProduct.setStock(product.getStock());
        currentProduct.setCategory(product.getCategory());
        currentProduct.setStatus("UPDATED");

        return currentProduct;
    }
}
