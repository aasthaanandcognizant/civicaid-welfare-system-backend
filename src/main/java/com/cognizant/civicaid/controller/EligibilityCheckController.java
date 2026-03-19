package com.cognizant.civicaid.controller;

import com.cognizant.civicaid.dto.request.EligibilityCheckRequest;
import com.cognizant.civicaid.dto.response.ApiResponse;
import com.cognizant.civicaid.dto.response.EligibilityCheckResponse;
import com.cognizant.civicaid.entity.User;
import com.cognizant.civicaid.exception.ResourceNotFoundException;
import com.cognizant.civicaid.repository.UserRepository;
import com.cognizant.civicaid.service.EligibilityCheckService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eligibility-checks")
@RequiredArgsConstructor
public class EligibilityCheckController {

    private final EligibilityCheckService eligibilityCheckService;
    private final UserRepository userRepository;

    @PostMapping
    @PreAuthorize("hasAnyRole('WELFARE_OFFICER','ADMINISTRATOR')")
    public ResponseEntity<ApiResponse<EligibilityCheckResponse>> performCheck(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody EligibilityCheckRequest request) {
        User officer = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        eligibilityCheckService.performCheck(officer.getUserId(), request),
                        "Eligibility check completed"));
    }

    @GetMapping("/application/{applicationId}")
    @PreAuthorize("hasAnyRole('WELFARE_OFFICER','ADMINISTRATOR','COMPLIANCE_OFFICER','GOVERNMENT_AUDITOR')")
    public ResponseEntity<ApiResponse<List<EligibilityCheckResponse>>> getChecksByApplication(
            @PathVariable Long applicationId) {
        return ResponseEntity.ok(ApiResponse.success(
                eligibilityCheckService.getChecksByApplication(applicationId)));
    }

    @GetMapping("/application/{applicationId}/latest")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<EligibilityCheckResponse>> getLatestCheck(
            @PathVariable Long applicationId) {
        return ResponseEntity.ok(ApiResponse.success(
                eligibilityCheckService.getLatestCheck(applicationId)));
    }
}
