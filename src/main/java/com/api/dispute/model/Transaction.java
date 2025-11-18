package com.api.dispute.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "TRANSACTION")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String externalId;
    private String accountNumber;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public Transaction(String externalId, String accountNumber, BigDecimal amount, String description, User user) {
        this.externalId = externalId;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.description = description;
        this.user = user;
    }
}