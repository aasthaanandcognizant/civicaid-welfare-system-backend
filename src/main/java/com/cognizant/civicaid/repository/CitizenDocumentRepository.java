package com.cognizant.civicaid.repository;

import com.cognizant.civicaid.entity.CitizenDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitizenDocumentRepository extends JpaRepository<CitizenDocument, Long> {
    List<CitizenDocument> findByCitizen_CitizenId(Long citizenId);
    List<CitizenDocument> findByCitizen_CitizenIdAndDocType(Long citizenId, CitizenDocument.DocType docType);
    List<CitizenDocument> findByVerificationStatus(CitizenDocument.VerificationStatus status);
    boolean existsByCitizen_CitizenIdAndDocType(Long citizenId, CitizenDocument.DocType docType);
}
