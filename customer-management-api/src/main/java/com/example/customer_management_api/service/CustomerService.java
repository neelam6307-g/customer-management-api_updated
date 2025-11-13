package com.example.customer_management_api.service;

import com.example.customer_management_api.dto.CustomerDTO;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    // create a new customer
    CustomerDTO createCustomer(CustomerDTO customerDTO);

    // fetch customer by id
    CustomerDTO getCustomerById(UUID id);

    // fetch by name or email
    CustomerDTO getCustomerByName(String name);
    CustomerDTO getCustomerByEmail(String email);

    // get all customers
    List<CustomerDTO> getAllCustomers();

    // update customer details
    CustomerDTO updateCustomer(UUID id, CustomerDTO customerDTO);

    // delete customer
    void deleteCustomer(UUID id);

    String determineCustomerTier(UUID id);
}
