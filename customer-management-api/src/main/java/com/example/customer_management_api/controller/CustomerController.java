package com.example.customer_management_api.controller;

import com.example.customer_management_api.dto.CustomerDTO;
import com.example.customer_management_api.service.CustomerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // create
    @PostMapping
    public CustomerDTO createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        log.info("POST /customers - creating new customer: {}", customerDTO.getEmail());
        return customerService.createCustomer(customerDTO);
    }

    // get by id
    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable UUID id) {
        log.debug("GET /customers/{}", id);
        return customerService.getCustomerById(id);
    }

    // get by name
    @GetMapping(params = "name")
    public CustomerDTO getCustomerByName(@RequestParam String name) {
        log.debug("GET /customers?name={}", name);
        return customerService.getCustomerByName(name);
    }

    // get by email
    @GetMapping(params = "email")
    public CustomerDTO getCustomerByEmail(@RequestParam String email) {
        log.debug("GET /customers?email={}", email);
        return customerService.getCustomerByEmail(email);
    }

    // get all
    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        log.info("GET /customers - fetching all");
        return customerService.getAllCustomers();
    }

    // update
    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable UUID id, @Valid @RequestBody CustomerDTO customerDTO) {
        log.info("PUT /customers/{} - updating", id);
        return customerService.updateCustomer(id, customerDTO);
    }

    // delete
    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable UUID id) {
        log.warn("DELETE /customers/{}", id);
        customerService.deleteCustomer(id);
        return "Customer deleted with id: " + id;
    }
}
