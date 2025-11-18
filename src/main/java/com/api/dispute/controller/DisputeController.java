// src/main/java/com/api/dispute/controller/DisputeController.java
package com.api.dispute.controller;

import com.api.dispute.model.Dispute;
import com.api.dispute.service.DisputeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/disputes")
@CrossOrigin(origins = "http://localhost:3000")
public class DisputeController {

    private final DisputeService disputeService;

    public DisputeController(DisputeService disputeService) {
        this.disputeService = disputeService;
    }

    @PostMapping
    public ResponseEntity<Dispute> create(
            @RequestBody Map<String, String> body,
            Authentication auth) {

        String username = auth.getName();
        Long transactionId = Long.valueOf(body.get("transactionId"));
        String reason = body.get("reason");

        Dispute dispute = disputeService.createDispute(username, transactionId, reason);
        return ResponseEntity.ok(dispute);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Dispute>> getUserDisputes(Authentication auth) {
        List<Dispute> disputes = disputeService.getDisputesByCurrentUser(auth.getName());
        return disputes.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(disputes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dispute> getDisputeById(@PathVariable Long id) {
        return ResponseEntity.ok(disputeService.getDisputeById(id));
    }
}