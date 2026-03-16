package com.cognizant.civicaid.entity;

import com.cognizant.civicaid.enums.DocType;
import com.cognizant.civicaid.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CitizenDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DocumentID")
    private Long documentId;

    // FK --
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CitizenID", nullable = false)
    private Citizen citizen;

    @Enumerated(EnumType.STRING)
    @Column(name = "DocType", nullable = false)
    private DocType docType;

    @Column(name = "FileURI", nullable = false)
    private String fileUri;

    @Column(name = "UploadedDate")
    private LocalDate uploadedDate;

    @Column(name = "VerificationStatus")
    private String verificationStatus;
}