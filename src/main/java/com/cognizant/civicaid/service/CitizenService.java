package com.cognizant.civicaid.service;

import com.cognizant.civicaid.dto.request.CitizenRequest;
import com.cognizant.civicaid.dto.response.CitizenResponse;
import com.cognizant.civicaid.entity.Citizen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CitizenService {
    CitizenResponse registerCitizen(Long userId, CitizenRequest request);
    CitizenResponse getCitizenById(Long id);
    CitizenResponse getCitizenByUserId(Long userId);
    CitizenResponse updateCitizen(Long id, CitizenRequest request);
    void updateCitizenStatus(Long id, Citizen.CitizenStatus status);
    Page<CitizenResponse> getAllCitizens(Pageable pageable);
    Page<CitizenResponse> getCitizensByStatus(Citizen.CitizenStatus status, Pageable pageable);
    Page<CitizenResponse> searchCitizens(String name, Pageable pageable);
}
