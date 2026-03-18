package com.cognizant.civicaid.entity;

import com.cognizant.civicaid.enums.ReportScope;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReportID")
    private Long reportId;

    //FK , PK in compliance record
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ComplianceID", nullable = false, unique = true)
    private ComplianceRecord complianceRecord;

    @Enumerated(EnumType.STRING)
    @Column(name = "Scope", nullable = false)
    private ReportScope scope;

    @Column(name = "Metrics", columnDefinition = "text")
    private String metrics;

    @Column(name = "GeneratedDate")
    private LocalDateTime generatedDate;
}