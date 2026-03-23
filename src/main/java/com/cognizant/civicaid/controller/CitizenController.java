package com.cognizant.civicaid.controller;

import com.cognizant.civicaid.dto.request.CitizenRequest;
import com.cognizant.civicaid.dto.response.CitizenResponse;
import com.cognizant.civicaid.entity.Citizen;
import com.cognizant.civicaid.entity.User;
import com.cognizant.civicaid.exception.ResourceNotFoundException;
import com.cognizant.civicaid.repository.UserRepository;
import com.cognizant.civicaid.service.CitizenService;
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

    @PostMapping("/registercitizen")
    @PreAuthorize("hasRole('CITIZEN')")
    public ResponseEntity<CitizenResponse> registerCitizen(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CitizenRequest request) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        CitizenResponse response = citizenService.registerCitizen(user.getUserId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CITIZEN')")
    public ResponseEntity<CitizenResponse> getMyProfile(
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ResponseEntity.ok(citizenService.getCitizenByUserId(user.getUserId()));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('CITIZEN')")
    public ResponseEntity<CitizenResponse> updateMyProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CitizenRequest request) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        CitizenResponse citizen = citizenService.getCitizenByUserId(user.getUserId());
        return ResponseEntity.ok(citizenService.updateCitizen(citizen.getCitizenId(), request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('WELFARE_OFFICER','PROGRAM_MANAGER','ADMINISTRATOR','COMPLIANCE_OFFICER')")
    public ResponseEntity<Page<CitizenResponse>> getAllCitizens(
            @PageableDefault(size = 20) Pageable pageable,
            @RequestParam(required = false) String search) {
        Page<CitizenResponse> result = search != null
                ? citizenService.searchCitizens(search, pageable)
                : citizenService.getAllCitizens(pageable);
        return ResponseEntity.ok((result));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('WELFARE_OFFICER','PROGRAM_MANAGER','ADMINISTRATOR','COMPLIANCE_OFFICER')")
    public ResponseEntity<CitizenResponse> getCitizenById(@PathVariable Long id) {
        return ResponseEntity.ok ((citizenService.getCitizenById(id)));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('WELFARE_OFFICER','ADMINISTRATOR')")
    public ResponseEntity<Void> updateCitizenStatus(
            @PathVariable Long id,
            @RequestParam Citizen.CitizenStatus status) {
        citizenService.updateCitizenStatus(id, status);
        return ResponseEntity.ok(null);
    }
}
