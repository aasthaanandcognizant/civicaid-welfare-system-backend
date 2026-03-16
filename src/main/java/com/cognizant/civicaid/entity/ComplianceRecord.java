package com.cognizant.civicaid.entity;

import com.cognizant.civicaid.enums.EntityType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ComplianceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ComplianceID")
    private Long complianceId;

    // This stores either ApplicationID or ProgramID based on Type
    @Column(name = "EntityID", nullable = false)
    private Long entityId;

    @Enumerated(EnumType.STRING)
    @Column(name = "Type", nullable = false, length = 30)
    private EntityType type;  // APPLICATION or PROGRAM

    @Column(name = "Result")
    private String result;

    @Column(name = "Date")
    private LocalDate date;

    @Column(name = "Notes", columnDefinition = "text")
    private String notes;

    //PK /// fetch - FK
    @OneToOne(mappedBy = "complianceRecord", cascade = CascadeType.ALL)
    private Report report;
}