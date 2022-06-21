package com.example.primermicro;

import java.util.Date;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.primermicro.entity.Category;
import com.example.primermicro.entity.Product;
import com.example.primermicro.repository.ProductRepository;
import com.example.primermicro.service.product.ProductService;
import com.example.primermicro.service.product.ProductServiceImplements;

@SpringBootTest
public class ProductServiceMockTest {
    @Mock
    private ProductRepository productRepository;

    private ProductService productService;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        productService = new ProductServiceImplements(productRepository);
        Product product = Product.builder()
                                 .id(1L)
                                 .name("Producto 1")
                                .description("Descripcion del producto 1")
                                .price(10.0)
                                .stock(10.0)
                                .category(Category.builder()
                                                    .id(1L)
                                                    .name("Categoria 1")
                                                    .build())
                                .status("ACTIVE")
                                .createAt(new Date())
                                .build();

        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));
        Mockito.when(productRepository.findById(2L))
                .thenReturn(Optional.empty());
    }

    @Test
    public void testGetProductById() {
        Product product = productService.getProductById(1L);
        Assertions.assertThat(product.getName()).isEqualTo("Producto 1");
    }

    //test updateStock
    @Test
    public void testUpdateStock() {
        Product product = productService.getProductById(1L);
        productService.updateStock(1L, 10.0);
        Assertions.assertThat(product.getStock()).isEqualTo(20.0);
    }

    //test product not found
    @Test
    public void testGetProductByIdNotFound() {
        Assertions.assertThat(productService.getProductById(2L)).isNull();
    }

    //test update stock to a product not found
    @Test
    public void testUpdateStockNotFound() {
        Assertions.assertThatNullPointerException()
                .isThrownBy(() -> productService.updateStock(2L, 10.0));
    }
}
