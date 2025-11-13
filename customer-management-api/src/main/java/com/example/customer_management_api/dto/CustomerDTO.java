package com.example.customer_management_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {

    private UUID id;
    private String name;
    private String email;
    private BigDecimal annualSpend;
    private OffsetDateTime lastPurchaseDate;
    private String tier;
}
