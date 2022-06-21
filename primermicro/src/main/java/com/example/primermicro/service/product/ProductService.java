package com.example.primermicro.service.product;

import java.util.List;

import com.example.primermicro.entity.Category;
import com.example.primermicro.entity.Product;

public interface ProductService {
    public List<Product> listAllProducts();
    public Product getProductById(Long id);

    public Product saveProduct(Product product);
    public Product updateProduct(Product product);
    public void deleteProduct(Long id);
    
    public List<Product> findByCategory(Category category);
    public Product updateStock(Long id, Double cantidad);
}
