package com.example.customer_management_api.mapper;

import com.example.customer_management_api.dto.CustomerDTO;
import com.example.customer_management_api.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    // Convert Entity -> DTO with tier calculation
    @Mapping(target = "tier", expression = "java(calculateTier(customer.getAnnualSpend(), customer.getLastPurchaseDate()))")
    CustomerDTO toDTO(Customer customer);

    // Convert DTO -> Entity
    Customer toEntity(CustomerDTO dto);

    // Update existing entity from DTO
    void updateCustomerFromDTO(CustomerDTO dto, @MappingTarget Customer entity);

    // Tier calculation logic
    default String calculateTier(BigDecimal spend, OffsetDateTime lastPurchaseDate) {
        if (spend == null || lastPurchaseDate == null) return "UNKNOWN";

        OffsetDateTime now = OffsetDateTime.now();
        long monthsSincePurchase = java.time.Duration.between(lastPurchaseDate, now).toDays() / 30;

        if (spend.compareTo(BigDecimal.valueOf(10000)) > 0 && monthsSincePurchase <= 6) {
            return "PLATINUM";
        } else if (spend.compareTo(BigDecimal.valueOf(1000)) > 0 && monthsSincePurchase <= 12) {
            return "GOLD";
        } else if (spend.compareTo(BigDecimal.valueOf(1000)) <= 0) {
            return "SILVER";
        } else {
            return "SILVER"; // fallback
        }
    }
}
