package com.cognizant.civicaid.entity;

import com.cognizant.civicaid.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WelfareApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ApplicationID")
    private Long applicationId;

    // FK
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CitizenID", nullable = false)
    private Citizen citizen;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ProgramID", nullable = false)
    private Program program;

    @Column(name = "SubmittedDate")
    private LocalDate submittedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false, length = 30)
    private Status status;

    //Mappings
    //PK
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = false)
    @Builder.Default
    private List<EligibilityCheck> checks = new ArrayList<>();

    // PK
    @OneToOne(mappedBy = "application", fetch = FetchType.LAZY)
    private Disbursement disbursement;
}