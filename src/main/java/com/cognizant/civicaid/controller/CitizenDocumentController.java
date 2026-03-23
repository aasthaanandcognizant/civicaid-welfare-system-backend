package com.cognizant.civicaid.controller;

import com.cognizant.civicaid.dto.request.CitizenDocumentRequest;
import com.cognizant.civicaid.dto.response.CitizenDocumentResponse;
import com.cognizant.civicaid.entity.CitizenDocument;
import com.cognizant.civicaid.service.CitizenDocumentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class CitizenDocumentController {

    private final CitizenDocumentService documentService;

    @PostMapping
    @PreAuthorize("hasAnyRole('CITIZEN','WELFARE_OFFICER','ADMINISTRATOR')")
    public ResponseEntity<CitizenDocumentResponse> uploadDocument(
            @Valid @RequestBody CitizenDocumentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(documentService.uploadDocument(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CitizenDocumentResponse> getDocumentById(@PathVariable Long id) {
        return ResponseEntity.ok((documentService.getDocumentById(id)));
    }

    @GetMapping("/citizen/{citizenId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CitizenDocumentResponse>> getDocumentsByCitizen(
            @PathVariable Long citizenId) {
        return ResponseEntity.ok((documentService.getDocumentsByCitizen(citizenId)));
    }

    @PatchMapping("/{id}/verify")
    @PreAuthorize("hasAnyRole('WELFARE_OFFICER','ADMINISTRATOR')")
    public ResponseEntity<CitizenDocumentResponse> verifyDocument(
            @PathVariable Long id,
            @RequestParam CitizenDocument.VerificationStatus status,
            @RequestParam(required = false) String notes) {
        return ResponseEntity.ok(
                documentService.verifyDocument(id, status, notes));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('WELFARE_OFFICER','ADMINISTRATOR')")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.ok(null);
    }
}
