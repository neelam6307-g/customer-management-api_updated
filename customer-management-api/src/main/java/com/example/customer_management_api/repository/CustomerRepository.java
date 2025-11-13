package com.example.customer_management_api.repository;

import com.example.customer_management_api.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByName(String name);
    Optional<Customer> findByEmail(String email);
}
