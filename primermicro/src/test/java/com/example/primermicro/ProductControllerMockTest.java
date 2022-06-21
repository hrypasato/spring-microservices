package com.example.primermicro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;

import com.example.primermicro.controller.ProductController;
import com.example.primermicro.entity.Category;
import com.example.primermicro.entity.Product;
import com.example.primermicro.service.product.ProductService;

@WebMvcTest(ProductController.class)
public class ProductControllerMockTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        //create three categories
        Category category1 = Category.builder().id(1L).build();
        Category category2 = Category.builder().id(2L).build();
        Category category3 = Category.builder().id(3L).build();

        //create two products with category 1
        Product product1 = Product.builder().id(1L).name("Producto 1").description("Descripcion del producto 1").price(10.0).stock(10.0).category(category1).status("ACTIVE").createAt(new Date()).build();
        Product product2 = Product.builder().id(2L).name("Producto 2").description("Descripcion del producto 2").price(20.0).stock(20.0).category(category1).status("ACTIVE").createAt(new Date()).build();

        //create one product with category 2
        Product product3 = Product.builder().id(3L).name("Producto 3").description("Descripcion del producto 3").price(30.0).stock(30.0).category(category2).status("ACTIVE").createAt(new Date()).build();

        //create one product with category 3
        Product product4 = Product.builder().id(4L).name("Producto 4").description("Descripcion del producto 4").price(40.0).stock(40.0).category(category3).status("ACTIVE").createAt(new Date()).build();
        
        //Product five
        Product product5 = Product.builder().id(5L).name("Producto 5").description("Descripcion del producto 5").price(50.0).stock(50.0).category(category1).status("ACTIVE").createAt(new Date()).build();

        //Product 1 updated
        Product product1Updated = Product.builder().id(1L).name("Producto 1").description("Descripcion del producto 1 updated").price(10.0).stock(10.0).category(category1).status("UPDATED").createAt(new Date()).build();

        //create a list of products
        List<Product> products = List.of(product1, product2, product3, product4);

        //mock the productService
        Mockito.when(productService.listAllProducts()).thenReturn(products);
        Mockito.when(productService.getProductById(1L)).thenReturn(product1);
        Mockito.when(productService.getProductById(2L)).thenReturn(product2);
        Mockito.when(productService.getProductById(3L)).thenReturn(product3);
        Mockito.when(productService.getProductById(4L)).thenReturn(product4);
        Mockito.when(productService.findByCategory(category2)).thenReturn(List.of(product3));
        Mockito.when(productService.getProductById(5L)).thenReturn(null);
        Mockito.when(productService.saveProduct(any())).thenReturn(product5);
        Mockito.when(productService.updateProduct(any())).thenReturn(product1Updated);
        Mockito.when(productService.updateStock(3L,10.0)).thenReturn(product3);
        
    }

    @Test
    public void testListAllProducts() throws Exception {
        mockMvc.perform(get("/products")).andExpect(status().isOk());
    }

    //test get product by category
    @Test
    public void testGetProductByCategory() throws Exception {
        mockMvc.perform(get("/products?category=2")).andExpect(status().isOk());
    }

    @Test
    public void testGetProductById() throws Exception {
        mockMvc.perform(get("/products/1")).andExpect(status().isOk());
    }

    @Test
    public void testGetProductByIdNotFound() throws Exception {
        mockMvc.perform(get("/products/5")).andExpect(status().isNoContent());
    }

    @Test
    public void testSaveProduct() throws Exception {
        mockMvc.perform(post("/products")
                .contentType("application/json")
                .content("{\"name\":\"Producto 5\",\"description\":\"Descripcion del producto 5\",\"price\":50.0,\"stock\":50.0,\"category\":{\"id\":1},\"status\":\"ACTIVE\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateProduct() throws Exception {
        //test update product 1
        mockMvc.perform(put("/products/1")
                .contentType("application/json")
                .content("{\"name\":\"Producto 1\",\"description\":\"Descripcion del producto 1 updated\",\"price\":10.0,\"stock\":10.0,\"category\":{\"id\":1},\"status\":\"UPDATED\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateProductNotFound() throws Exception {
        //test update product 5
        mockMvc.perform(put("/products/5")
                .contentType("application/json")
                .content("{\"name\":\"Producto 5\",\"description\":\"Descripcion del producto 5\",\"price\":50.0,\"stock\":50.0,\"category\":{\"id\":1},\"status\":\"ACTIVE\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteProduct() throws Exception {
        mockMvc.perform(delete("/products/3")).andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteProductNotFound() throws Exception {
        mockMvc.perform(delete("/products/5")).andExpect(status().isNotFound());
    }
    
    //test update stock
    @Test
    public void testUpdateStock() throws Exception {
        mockMvc.perform(put("/products/3/stock?cantidad=10")).andExpect(status().isOk());
    }

    @Test
    public void testUpdateStockNotFound() throws Exception {
        mockMvc.perform(put("/products/5/stock?cantidad=2")).andExpect(status().isNotFound());
    }

    //test create product with stock 0 and name null
    @Test
    public void testCreateProductWithStock0AndNameNull() throws Exception {
        mockMvc.perform(post("/products")
                .contentType("application/json")
                .content("{\"name\":null,\"description\":\"Descripcion del producto 5\",\"price\":50.0,\"stock\":0,\"category\":{\"id\":1},\"status\":\"ACTIVE\"}"))
                .andExpect(status().isBadRequest());
    }
    
}
