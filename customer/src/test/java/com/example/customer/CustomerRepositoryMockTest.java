package com.example.customer;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.customer.entity.Customer;
import com.example.customer.entity.Region;
import com.example.customer.repository.CustomerRepository;

@DataJpaTest
public class CustomerRepositoryMockTest {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    //test findByRegion
    @Test
    public void testFindByRegion(){
        Customer customer = Customer.builder()
                .firstName("Juan")
                .lastName("Perez")
                .email("juan@gmail.com")
                .numberID("12345678")
                .region(Region.builder().id(1L).build())
                .build();
        customerRepository.save(customer);

        List<Customer> customers = customerRepository.findByRegion(Region.builder().id(1L).build());
        Assertions.assertEquals(2, customers.size());
    }
}
