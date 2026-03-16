package com.cognizant.civicaid.entity;

import com.cognizant.civicaid.enums.PaymentMethod;
import com.cognizant.civicaid.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PaymentID")
    private Long paymentId;

    // FK lives here, PK in disbursement
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DisbursementID", nullable = false)
    private Disbursement disbursement;

    @Enumerated(EnumType.STRING)
    @Column(name = "Method", nullable = false)
    private PaymentMethod method;

    @Column(name = "Date")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    private Status status;
}