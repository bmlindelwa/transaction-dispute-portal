
package com.api.dispute.service;

import com.api.dispute.exception.ResourceNotFoundException;
import com.api.dispute.model.Transaction;
import com.api.dispute.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public List<Transaction> findByAccountNumber(String accountNumber) {
        return transactionRepository.findByAccountNumber(accountNumber);
    }

    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }
}