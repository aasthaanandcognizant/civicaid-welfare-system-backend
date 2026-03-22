package com.civicaid.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "citizen_documents")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CitizenDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizen_id", nullable = false)
    private Citizen citizen;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocType docType;

    @Column(nullable = false)
    private String fileUri;

    @CreationTimestamp
    private LocalDateTime uploadedDate;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;

    private String verificationNotes;

    public enum DocType {
        ID_PROOF, RESIDENCE, INCOME_PROOF, BIRTH_CERTIFICATE, OTHER
    }

    public enum VerificationStatus {
        PENDING, VERIFIED, REJECTED
    }
}
