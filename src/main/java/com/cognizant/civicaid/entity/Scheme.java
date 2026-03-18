package com.cognizant.civicaid.entity;

import com.cognizant.civicaid.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Scheme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SchemeID")
    private Long schemeId;

    // FK, PK in program
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ProgramID", nullable = false)
    private Program program;

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "Description", columnDefinition = "text")
    private String description;

    @Column(name = "EligibilityCriteria", columnDefinition = "text")
    private String eligibilityCriteria;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    private Status status;
}