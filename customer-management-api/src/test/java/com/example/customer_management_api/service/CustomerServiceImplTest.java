package com.example.customer_management_api.service;

import com.example.customer_management_api.dto.CustomerDTO;
import com.example.customer_management_api.entity.Customer;
import com.example.customer_management_api.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer existingCustomer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        existingCustomer = Customer.builder()
                .id(UUID.randomUUID())
                .name("Neelam")
                .email("neelam@example.com")
                .annualSpend(BigDecimal.valueOf(7500))
                .lastPurchaseDate(OffsetDateTime.now())
                .build();
    }

    // ===== CREATE =====
    @Test
    void createCustomer_ShouldReturnSavedCustomer() {
        CustomerDTO dto = CustomerDTO.builder()
                .name("Neelam")
                .email("neelam@example.com")
                .annualSpend(BigDecimal.valueOf(7500))
                .lastPurchaseDate(OffsetDateTime.now())
                .build();

        when(customerRepository.save(any(Customer.class))).thenReturn(existingCustomer);

        CustomerDTO result = customerService.createCustomer(dto);

        assertNotNull(result);
        assertEquals("Neelam", result.getName());
        assertEquals("SILVER", result.getTier());  // Silver tier
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    // ===== GET BY ID =====
    @Test
    void getCustomerById_ShouldReturnCustomer() {
        when(customerRepository.findById(existingCustomer.getId()))
                .thenReturn(Optional.of(existingCustomer));

        CustomerDTO result = customerService.getCustomerById(existingCustomer.getId());

        assertEquals(existingCustomer.getName(), result.getName());
        assertEquals("SILVER", result.getTier());
    }

    @Test
    void getCustomerById_NotFound_ShouldThrowException() {
        UUID randomId = UUID.randomUUID();
        when(customerRepository.findById(randomId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> customerService.getCustomerById(randomId));
    }

    // ===== UPDATE =====
    @Test
    void updateCustomer_ShouldUpdateFields() {
        when(customerRepository.findById(existingCustomer.getId()))
                .thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(existingCustomer);

        CustomerDTO updateDTO = CustomerDTO.builder()
                .name("Neelam Updated")
                .email("neelam@example.com")
                .annualSpend(BigDecimal.valueOf(15000))  // Platinum
                .lastPurchaseDate(OffsetDateTime.now())
                .build();

        CustomerDTO updated = customerService.updateCustomer(existingCustomer.getId(), updateDTO);

        assertEquals("Neelam Updated", updated.getName());
        assertEquals("PLATINUM", updated.getTier());  // Platinum tier
    }

    // ===== DELETE =====
    @Test
    void deleteCustomer_ShouldCallRepository() {
        doNothing().when(customerRepository).deleteById(existingCustomer.getId());

        customerService.deleteCustomer(existingCustomer.getId());

        verify(customerRepository, times(1)).deleteById(existingCustomer.getId());
    }

    // ===== TIER CALCULATION =====
    @Test
    void testCustomerTierCalculation() {
        // Silver customer
        Customer silver = Customer.builder()
                .id(UUID.randomUUID())
                .name("Silver Customer")
                .email("silver@example.com")
                .annualSpend(BigDecimal.valueOf(500))
                .lastPurchaseDate(OffsetDateTime.now())
                .build();

        // Gold customer
        Customer gold = Customer.builder()
                .id(UUID.randomUUID())
                .name("Gold Customer")
                .email("gold@example.com")
                .annualSpend(BigDecimal.valueOf(5000))
                .lastPurchaseDate(OffsetDateTime.now())
                .build();

        // Platinum customer
        Customer platinum = Customer.builder()
                .id(UUID.randomUUID())
                .name("Platinum Customer")
                .email("platinum@example.com")
                .annualSpend(BigDecimal.valueOf(15000))
                .lastPurchaseDate(OffsetDateTime.now())
                .build();

        when(customerRepository.findById(silver.getId())).thenReturn(Optional.of(silver));
        when(customerRepository.findById(gold.getId())).thenReturn(Optional.of(gold));
        when(customerRepository.findById(platinum.getId())).thenReturn(Optional.of(platinum));

        assertEquals("SILVER", customerService.determineCustomerTier(silver.getId()));
        assertEquals("GOLD", customerService.determineCustomerTier(gold.getId()));
        assertEquals("PLATINUM", customerService.determineCustomerTier(platinum.getId()));
    }

    // ===== EMAIL VALIDATION =====
    @Test
    void createCustomer_InvalidEmail_ShouldThrowException() {
        CustomerDTO dto = CustomerDTO.builder()
                .name("Test User")
                .email("invalid-email") // invalid format
                .annualSpend(BigDecimal.valueOf(500))
                .build();

        // Normally Spring validates @Email, simulate by throwing manually
        assertThrows(Exception.class, () -> customerService.createCustomer(dto));
    }

}
