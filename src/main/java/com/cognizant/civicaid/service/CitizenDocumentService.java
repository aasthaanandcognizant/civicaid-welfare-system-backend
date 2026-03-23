package com.cognizant.civicaid.service;

import com.cognizant.civicaid.dto.request.CitizenDocumentRequest;
import com.cognizant.civicaid.dto.response.CitizenDocumentResponse;
import com.cognizant.civicaid.entity.CitizenDocument;

import java.util.List;

public interface CitizenDocumentService {
    CitizenDocumentResponse uploadDocument(CitizenDocumentRequest request);
    CitizenDocumentResponse getDocumentById(Long id);
    List<CitizenDocumentResponse> getDocumentsByCitizen(Long citizenId);
    CitizenDocumentResponse verifyDocument(Long id, CitizenDocument.VerificationStatus status, String notes);
    void deleteDocument(Long id);
}
