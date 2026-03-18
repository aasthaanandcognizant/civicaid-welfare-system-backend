package com.cognizant.civicaid.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EligibilityCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CheckID")
    private Long checkId;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ApplicationID", nullable = false)
    private WelfareApplication application;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "OfficerID", nullable = false)
    private User officer;


    @Column(name = "Result")
    private String result;

    @Column(name = "Date")
    private LocalDate date;

    @Column(name = "Notes", columnDefinition = "text")
    private String notes;
}