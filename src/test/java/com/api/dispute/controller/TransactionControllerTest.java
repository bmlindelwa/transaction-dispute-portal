package com.api.dispute.controller;

import com.api.dispute.config.SecurityDisabledConfig;
import com.api.dispute.model.Transaction;
import com.api.dispute.repository.TransactionRepository;
import com.api.dispute.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
@Import(SecurityDisabledConfig.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = { TransactionController.class, SecurityDisabledConfig.class })
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;
    @MockBean
    private TransactionRepository transactionRepository;

    @Test
    void testGetTransactionByIdSuccess() throws Exception {
        Transaction t = new Transaction();
        t.setId(1L);
        t.setAmount(BigDecimal.valueOf(500.0));

        when(transactionService.findById(1L))
                .thenReturn(Optional.of(t));

        mockMvc.perform(get("/api/transactions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.amount").value(500.0));
    }

    @Test
    void testGetTransactionByIdNotFound() throws Exception {
        Long id = 99L;
        when(transactionService.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/transactions/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    void testGetAllTransactions() throws Exception {
        Transaction t1 = new Transaction();
        t1.setId(1L);

        Transaction t2 = new Transaction();
        t2.setId(2L);

        when(transactionService.findAll())
                .thenReturn(Arrays.asList(t1, t2));

        mockMvc.perform(get("/api/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}
