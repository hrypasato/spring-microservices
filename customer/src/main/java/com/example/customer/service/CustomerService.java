package com.example.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.customer.entity.Customer;
import com.example.customer.entity.Region;
import com.example.customer.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findCustomersByRegion(Region region) {
        return customerRepository.findByRegion(region);
    }

    @Override
    public Customer findCustomerID(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        if (customer.getId() != null) {
            Customer customerDB = findCustomerID(customer.getId());
            return customerDB;
        }

        return saveCustomer(customer, "CREATED");
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return saveCustomer(customer, "UPDATED");
    }

    @Override
    public Customer deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        return saveCustomer(customer, "DELETED");
    }

    private Customer saveCustomer(Customer customer, String state) {
        customer.setState(state);
        return customerRepository.save(customer);
    }
    
}
