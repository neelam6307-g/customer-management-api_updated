package com.example.customer_management_api.service;

import com.example.customer_management_api.dto.CustomerDTO;
import com.example.customer_management_api.entity.Customer;
import com.example.customer_management_api.exception.CustomerNotFoundException;
import com.example.customer_management_api.mapper.CustomerMapper;
import com.example.customer_management_api.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerServiceImpl implements com.example.customer_management_api.service.CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        log.info("Creating new customer: {}", customerDTO.getEmail());
        Customer entity = customerMapper.toEntity(customerDTO);
        Customer saved = customerRepository.save(entity);
        return customerMapper.toDTO(saved);
    }

    @Override
    public CustomerDTO getCustomerById(UUID id) {
        log.debug("Fetching customer by id: {}", id);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> CustomerNotFoundException.byId(id));
        return customerMapper.toDTO(customer);
    }

    @Override
    public CustomerDTO getCustomerByName(String name) {
        log.debug("Fetching customer by name: {}", name);
        Customer customer = customerRepository.findByName(name)
                .orElseThrow(() -> CustomerNotFoundException.byName(name));
        return customerMapper.toDTO(customer);
    }

    @Override
    public CustomerDTO getCustomerByEmail(String email) {
        log.debug("Fetching customer by email: {}", email);
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> CustomerNotFoundException.byEmail(email));
        return customerMapper.toDTO(customer);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        log.info("Fetching all customers");
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO updateCustomer(UUID id, CustomerDTO customerDTO) {
        log.info("Updating customer with id: {}", id);
        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> CustomerNotFoundException.byId(id));

        customerMapper.updateCustomerFromDTO(customerDTO, existing);
        Customer updated = customerRepository.save(existing);
        return customerMapper.toDTO(updated);
    }

    @Override
    public void deleteCustomer(UUID id) {
        log.warn("Deleting customer with id: {}", id);
        if (!customerRepository.existsById(id)) {
            throw CustomerNotFoundException.byId(id);
        }
        customerRepository.deleteById(id);
    }

    @Override
    public String determineCustomerTier(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> CustomerNotFoundException.byId(id));
        return customerMapper.calculateTier(customer.getAnnualSpend(), customer.getLastPurchaseDate());
    }
}
