package com.api.dispute.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "DISPUTE")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Dispute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Transaction transaction;

    @ManyToOne
    private User user;

    private String reason;
    private String status; // OPEN, RESOLVED, REJECTED, PENDING
    private Instant createdAt;

    public Dispute(Transaction transaction, User user, String reason, String status, Instant createdAt){
         this.transaction=transaction; this.user=user; this.reason=reason; this.status=status; this.createdAt=createdAt;
    }
}
