package com.example.customer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.customer.entity.Customer;
import com.example.customer.entity.Region;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Optional<Customer> findById(Long id);
    public Optional<Customer> findByNumberID(String numberID);
    public List<Customer> findByLastName(String lastName);
    public List<Customer> findByRegion(Region region);
}
