package com.example.customer;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.ArgumentMatchers.any;

import com.example.customer.entity.Customer;
import com.example.customer.entity.Region;
import com.example.customer.repository.CustomerRepository;
import com.example.customer.service.CustomerService;

@SpringBootTest
public class CustomerServiceMockTest {
    @Mock
    private CustomerRepository customerRepository;

    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerService(customerRepository);
        //build customer
        Customer customer = Customer.builder()
                .firstName("Juan")
                .lastName("Perez")
                .email("juan@mail.com")
                .numberID("12345678")
                .region(Region.builder().id(1L).build())
                .build();

        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        Mockito.when(customerRepository.findById(2L)).thenReturn(Optional.empty());
        Mockito.when(customerRepository.findAll()).thenReturn(List.of(customer));
        Mockito.when(customerRepository.findByRegion(any())).thenReturn(List.of(customer));
    }

    @Test
    public void testFindCustomerID() {
        Customer customer = customerService.findCustomerID(1L);
        Assertions.assertEquals("Juan", customer.getFirstName());
    }

    @Test
    public void testFindCustomerIDNotFound() {
        Customer customer = customerService.findCustomerID(2L);
        Assertions.assertNull(customer);
    }

    @Test
    public void testFindAllCustomers() {
        List<Customer> customers = customerService.findAllCustomers();
        Assertions.assertEquals(1, customers.size());
    }

    @Test
    public void testFindCustomersByRegion() {
        List<Customer> customers = customerService.findCustomersByRegion(Region.builder().id(1L).build());
        Assertions.assertEquals(1, customers.size());
    }
    
}
