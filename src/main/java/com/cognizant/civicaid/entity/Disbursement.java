package com.cognizant.civicaid.entity;

import com.cognizant.civicaid.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Disbursement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DisbursementID")
    private Long disbursementId;

    //FK - can fetch from 4elfare application
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ApplicationID", nullable = false, unique = true)
    private WelfareApplication application;


    @Column(name = "ApplicationID")
    private Long applicationIdRef;

    @Column(name = "Amount")
    private BigDecimal amount;

    @Column(name = "Date")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private Status status;


    @OneToOne(mappedBy = "disbursement", cascade = CascadeType.ALL)
    private Payment payment;
}
