package com.example.customer.service;

import java.util.List;

import com.example.customer.entity.Customer;
import com.example.customer.entity.Region;

public interface ICustomerService {
    public List<Customer> findAllCustomers();
    public List<Customer> findCustomersByRegion(Region region);
    public Customer findCustomerID(Long id);
    public Customer createCustomer(Customer customer);
    public Customer updateCustomer(Customer customer);
    public Customer deleteCustomer(Long id);
}
