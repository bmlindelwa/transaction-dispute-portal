package com.api.dispute.service;

import com.api.dispute.exception.ResourceNotFoundException;
import com.api.dispute.model.Transaction;
import com.api.dispute.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {

    private final TransactionRepository repository = Mockito.mock(TransactionRepository.class);
    private final TransactionService service = new TransactionService(repository);

    @Test
    void testFindTransactionSuccess() {
        Transaction t = new Transaction();
        t.setId(10L);

        Mockito.when(repository.findById(10L))
                .thenReturn(Optional.of(t));

        Optional<Transaction> result = service.findById(10L);
        assertEquals(10L, result.get().getId());
    }
}
