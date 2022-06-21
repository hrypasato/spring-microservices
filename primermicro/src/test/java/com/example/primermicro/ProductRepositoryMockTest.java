package com.example.primermicro;

import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.primermicro.entity.Category;
import com.example.primermicro.entity.Product;
import com.example.primermicro.repository.ProductRepository;

@DataJpaTest
public class ProductRepositoryMockTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testFindByCategory() {
        Product product = Product.builder()
                                .name("Product 1")
                                .description("Description 1")
                                .stock(1.0)
                                .price(1.0)
                                .status("A")
                                .createAt(new Date())
                                .category(Category.builder().id(1L).build())
                                .build();

        productRepository.save(product);

        List<Product> products = productRepository.findByCategory(product.getCategory());
        Assertions.assertThat(products.size()).isEqualTo(3);
    }
}
