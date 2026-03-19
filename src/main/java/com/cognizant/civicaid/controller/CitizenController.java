package com.cognizant.civicaid.controller;

import com.civicaid.dto.request.CitizenRequest;
import com.civicaid.dto.response.ApiResponse;
import com.civicaid.dto.response.CitizenResponse;
import com.civicaid.entity.Citizen;
import com.civicaid.entity.User;
import com.civicaid.exception.ResourceNotFoundException;
import com.civicaid.repository.UserRepository;
import com.civicaid.service.CitizenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/citizens")
@RequiredArgsConstructor
public class CitizenController {

    private final CitizenService citizenService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    @PreAuthorize("hasRole('CITIZEN')")
    public ResponseEntity<ApiResponse<CitizenResponse>> registerCitizen(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CitizenRequest request) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        CitizenResponse response = citizenService.registerCitizen(user.getUserId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Citizen profile created successfully"));
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('CITIZEN')")
    public ResponseEntity<ApiResponse<CitizenResponse>> getMyProfile(
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ResponseEntity.ok(ApiResponse.success(citizenService.getCitizenByUserId(user.getUserId())));
    }

    @PutMapping("/me")
    @PreAuthorize("hasRole('CITIZEN')")
    public ResponseEntity<ApiResponse<CitizenResponse>> updateMyProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CitizenRequest request) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        CitizenResponse citizen = citizenService.getCitizenByUserId(user.getUserId());
        return ResponseEntity.ok(ApiResponse.success(
                citizenService.updateCitizen(citizen.getCitizenId(), request), "Profile updated"));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('WELFARE_OFFICER','PROGRAM_MANAGER','ADMINISTRATOR','COMPLIANCE_OFFICER')")
    public ResponseEntity<ApiResponse<Page<CitizenResponse>>> getAllCitizens(
            @PageableDefault(size = 20) Pageable pageable,
            @RequestParam(required = false) String search) {
        Page<CitizenResponse> result = search != null
                ? citizenService.searchCitizens(search, pageable)
                : citizenService.getAllCitizens(pageable);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('WELFARE_OFFICER','PROGRAM_MANAGER','ADMINISTRATOR','COMPLIANCE_OFFICER')")
    public ResponseEntity<ApiResponse<CitizenResponse>> getCitizenById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(citizenService.getCitizenById(id)));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('WELFARE_OFFICER','ADMINISTRATOR')")
    public ResponseEntity<ApiResponse<Void>> updateCitizenStatus(
            @PathVariable Long id,
            @RequestParam Citizen.CitizenStatus status) {
        citizenService.updateCitizenStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success(null, "Citizen status updated"));
    }
}
