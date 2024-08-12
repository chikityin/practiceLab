package org.example.practice_lab_trial3.web;

import org.example.practice_lab_trial3.entities.Customer;
import org.example.practice_lab_trial3.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
@WebAppConfiguration
@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {
    Customer customer;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    View mockView;

    @InjectMocks
    CustomerController customerController;

    @BeforeEach
    void setup() throws ParseException {
        customer = new Customer();
        customer.setCustomerNumber(1L);
        customer.setCustomerName("John");
        customer.setCustomerDeposit(3000);
        customer.setNumberOfYears(5);
        customer.setSavingsType("Saving-Deluxe");


        MockitoAnnotations.openMocks(this);

        mockMvc = standaloneSetup(customerController).setSingleView(mockView).build();
    }

    @Test
    void getCustomers() {
    }

    @Test
    void getCustomerForm() {
    }

    @Test
    void save() {
        when(customerRepository.save(customer)).thenReturn(customer);

        customerRepository.save(customer);

        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void edit() {
    }

    @Test
    void delete() {
        ArgumentCaptor<Long> idCapture = ArgumentCaptor.forClass(Long.class);

        doNothing().when(customerRepository).deleteById(idCapture.capture());

        customerRepository.deleteById(1L);

        assertEquals(1L, idCapture.getValue());

        verify(customerRepository, times(1)).deleteById(1L);
    }

    @Test
    void editCustomers() throws Exception {
        Customer c2 = new Customer();
        customer.setCustomerNumber(1L);
        customer.setCustomerName("John");
        customer.setCustomerDeposit(3000);
        customer.setNumberOfYears(5);
        customer.setSavingsType("Saving-Deluxe");

        Long iid = 1L;

        when(customerRepository.findById(iid)).thenReturn(Optional.of(c2));

        mockMvc.perform(get("/editCustomers").param("id", String.valueOf(1L)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("customer", c2))
                .andExpect(view().name("editCustomers"));

        verify(customerRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void getProjectedInvestment() {
    }
}