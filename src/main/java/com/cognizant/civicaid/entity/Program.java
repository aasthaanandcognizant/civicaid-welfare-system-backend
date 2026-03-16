package com.cognizant.civicaid.entity;

import com.cognizant.civicaid.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProgramID")
    private Long programId;

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "Description", columnDefinition = "text")
    private String description;

    @Column(name = "StartDate")
    private LocalDate startDate;

    @Column(name = "EndDate")
    private LocalDate endDate;

    @Column(name = "Budget")
    private BigDecimal budget;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private Status status;

    //FK
    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = false)
    @Builder.Default
    private List<Scheme> schemes = new ArrayList<>();

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = false)
    @Builder.Default
    private List<WelfareApplication> applications = new ArrayList<>();
}
