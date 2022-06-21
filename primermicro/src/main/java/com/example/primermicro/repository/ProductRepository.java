package com.example.primermicro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.primermicro.entity.Category;
import com.example.primermicro.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public List<Product> findByCategory(Category category);

}
