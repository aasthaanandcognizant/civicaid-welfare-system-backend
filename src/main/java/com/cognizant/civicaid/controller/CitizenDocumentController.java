package com.cognizant.civicaid.controller;

import com.civicaid.dto.request.CitizenDocumentRequest;
import com.civicaid.dto.response.ApiResponse;
import com.civicaid.dto.response.CitizenDocumentResponse;
import com.civicaid.entity.CitizenDocument;
import com.civicaid.service.CitizenDocumentService;
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
    public ResponseEntity<ApiResponse<CitizenDocumentResponse>> uploadDocument(
            @Valid @RequestBody CitizenDocumentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(documentService.uploadDocument(request), "Document uploaded successfully"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<CitizenDocumentResponse>> getDocumentById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(documentService.getDocumentById(id)));
    }

    @GetMapping("/citizen/{citizenId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<CitizenDocumentResponse>>> getDocumentsByCitizen(
            @PathVariable Long citizenId) {
        return ResponseEntity.ok(ApiResponse.success(documentService.getDocumentsByCitizen(citizenId)));
    }

    @PatchMapping("/{id}/verify")
    @PreAuthorize("hasAnyRole('WELFARE_OFFICER','ADMINISTRATOR')")
    public ResponseEntity<ApiResponse<CitizenDocumentResponse>> verifyDocument(
            @PathVariable Long id,
            @RequestParam CitizenDocument.VerificationStatus status,
            @RequestParam(required = false) String notes) {
        return ResponseEntity.ok(ApiResponse.success(
                documentService.verifyDocument(id, status, notes), "Document verification updated"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('WELFARE_OFFICER','ADMINISTRATOR')")
    public ResponseEntity<ApiResponse<Void>> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Document deleted"));
    }
}
