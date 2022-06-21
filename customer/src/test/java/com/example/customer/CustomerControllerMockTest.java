package com.example.customer;

import java.util.List;

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

import com.example.customer.controller.CustomerController;
import com.example.customer.entity.Customer;
import com.example.customer.entity.Region;
import com.example.customer.service.CustomerService;

@WebMvcTest(CustomerController.class)
public class CustomerControllerMockTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private CustomerService customerService;

        @BeforeEach
        public void setUp() {
                MockitoAnnotations.initMocks(this);

                // create three regions
                Region region1 = Region.builder().id(1L).build();
                Region region2 = Region.builder().id(2L).build();
                Region region3 = Region.builder().id(3L).build();

                // create three customers
                Customer customer1 = Customer.builder()
                                .firstName("Juan")
                                .lastName("Perez")
                                .email("juan@mail.com")
                                .numberID("12345678")
                                .region(region1)
                                .build();

                Customer customer2 = Customer.builder()
                                .firstName("Pedro")
                                .lastName("Perez")
                                .email("pedro@mail.com")
                                .numberID("87654321")
                                .region(region2)
                                .build();

                Customer customer3 = Customer.builder()
                                .firstName("Maria")
                                .lastName("Perez")
                                .email("maria@mail.com")
                                .numberID("12345678")
                                .region(region3)
                                .build();

                // mock the customer service
                Mockito.when(customerService.findCustomerID(1L)).thenReturn(customer1);
                Mockito.when(customerService.findCustomerID(2L)).thenReturn(customer2);
                Mockito.when(customerService.findCustomerID(3L)).thenReturn(customer3);
                Mockito.when(customerService.findAllCustomers()).thenReturn(List.of(customer1, customer2, customer3));
                Mockito.when(customerService.findCustomersByRegion(region1)).thenReturn(List.of(customer1));
                Mockito.when(customerService.findCustomersByRegion(region2)).thenReturn(List.of(customer2));
                Mockito.when(customerService.findCustomersByRegion(region3)).thenReturn(List.of(customer3));

        }

        @Test
        public void testFindCustomerID() throws Exception {
                mockMvc.perform(get("/customers/1"))
                                .andExpect(status().isOk());
        }

        @Test
        public void testFindAllCustomers() throws Exception {
                mockMvc.perform(get("/customers"))
                                .andExpect(status().isOk());
        }

        @Test
        public void testFindCustomersByRegion() throws Exception {
                mockMvc.perform(get("/customers?regionId=1"))
                                .andExpect(status().isOk());
        }

        @Test
        public void testCreateCustomer() throws Exception {
                mockMvc.perform(post("/customers")
                                .contentType("application/json")
                                .content("{\"firstName\":\"Juan\",\"lastName\":\"Perez\",\"email\":\"juan@mail.com\",\"numberID\":\"12345678\",\"region\":{\"id\":1}}"))
                                .andExpect(status().isCreated());
        }

        @Test
        public void testUpdateCustomer() throws Exception {     
                mockMvc.perform(put("/customers/1")
                        .contentType("application/json")
                        .content("{\"firstName\":\"Juan\",\"lastName\":\"Perez\",\"email\":\"juan@mail.com\",\"numberID\":\"12345678\",\"region\":{\"id\":1}}"))
                        .andExpect(status().isOk());

        }

        @Test
        public void testDeleteCustomer() throws Exception {
                mockMvc.perform(delete("/customers/1"))
                                .andExpect(status().isOk());
        }

}
