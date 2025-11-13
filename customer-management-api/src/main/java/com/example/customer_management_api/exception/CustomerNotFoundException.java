package com.example.customer_management_api.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String message) {
        super(message);
    }

    // optional helper methods (just for clarity)
    public static CustomerNotFoundException byId(java.util.UUID id) {
        return new CustomerNotFoundException("Customer not found with id: " + id);
    }

    public static CustomerNotFoundException byEmail(String email) {
        return new CustomerNotFoundException("Customer not found with email: " + email);
    }

    public static CustomerNotFoundException byName(String name) {
        return new CustomerNotFoundException("Customer not found with name: " + name);
    }
}
