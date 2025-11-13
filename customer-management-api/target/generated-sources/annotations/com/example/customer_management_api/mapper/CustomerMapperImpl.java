package com.example.customer_management_api.mapper;

import com.example.customer_management_api.dto.CustomerDTO;
import com.example.customer_management_api.entity.Customer;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-13T11:30:43+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public CustomerDTO toDTO(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerDTO.CustomerDTOBuilder customerDTO = CustomerDTO.builder();

        customerDTO.id( customer.getId() );
        customerDTO.name( customer.getName() );
        customerDTO.email( customer.getEmail() );
        customerDTO.annualSpend( customer.getAnnualSpend() );
        customerDTO.lastPurchaseDate( customer.getLastPurchaseDate() );

        customerDTO.tier( calculateTier(customer.getAnnualSpend(), customer.getLastPurchaseDate()) );

        return customerDTO.build();
    }

    @Override
    public Customer toEntity(CustomerDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Customer.CustomerBuilder customer = Customer.builder();

        customer.id( dto.getId() );
        customer.name( dto.getName() );
        customer.email( dto.getEmail() );
        customer.annualSpend( dto.getAnnualSpend() );
        customer.lastPurchaseDate( dto.getLastPurchaseDate() );

        return customer.build();
    }

    @Override
    public void updateCustomerFromDTO(CustomerDTO dto, Customer entity) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        entity.setName( dto.getName() );
        entity.setEmail( dto.getEmail() );
        entity.setAnnualSpend( dto.getAnnualSpend() );
        entity.setLastPurchaseDate( dto.getLastPurchaseDate() );
    }
}
