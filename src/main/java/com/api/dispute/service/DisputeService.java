// src/main/java/com/api/dispute/service/DisputeService.java
package com.api.dispute.service;

import com.api.dispute.exception.ResourceNotFoundException;
import com.api.dispute.exception.UsernameNotFoundException;
import com.api.dispute.model.Dispute;
import com.api.dispute.model.Transaction;
import com.api.dispute.model.User;
import com.api.dispute.repository.DisputeRepository;
import com.api.dispute.repository.TransactionRepository;
import com.api.dispute.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class DisputeService {

    private final DisputeRepository disputeRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public DisputeService(
            DisputeRepository disputeRepository,
            TransactionRepository transactionRepository,
            UserRepository userRepository) {
        this.disputeRepository = disputeRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Dispute createDispute(String username, Long transactionId, String reason) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + transactionId));

        Dispute dispute = new Dispute();
        dispute.setUser(user);
        dispute.setTransaction(transaction);
        dispute.setReason(reason);
        dispute.setStatus("OPEN");
        dispute.setCreatedAt(Instant.now());

        return disputeRepository.save(dispute);
    }

    public List<Dispute> getDisputesByCurrentUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return disputeRepository.findByUser(user);
    }

    public Dispute getDisputeById(Long id) {
        return disputeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dispute not found with id: " + id));
    }
}