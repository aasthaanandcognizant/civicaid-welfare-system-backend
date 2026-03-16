package com.cognizant.civicaid.entity;

import com.cognizant.civicaid.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AuditID")
    private Long auditId;


    // Audit.officer (FK = OfficerID → AppUser.userId)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "OfficerID", nullable = false) //this not need to match
    private AppUser officer;

    @Column(name = "Scope")
    private String scope;

    @Column(name = "Findings", columnDefinition = "text")
    private String findings;

    @Column(name = "Date")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private Status status;
}
