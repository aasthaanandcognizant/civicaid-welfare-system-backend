package com.cognizant.civicaid.service;

import com.cognizant.civicaid.dto.request.DisbursementRequest;
import com.cognizant.civicaid.dto.response.DisbursementResponse;
import com.cognizant.civicaid.entity.Disbursement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface DisbursementService {
    DisbursementResponse createDisbursement(DisbursementRequest request);
    DisbursementResponse getDisbursementById(Long id);
    List<DisbursementResponse> getDisbursementsByApplication(Long applicationId);
    Page<DisbursementResponse> getDisbursementsByStatus(Disbursement.DisbursementStatus status, Pageable pageable);
    Page<DisbursementResponse> getAllDisbursements(Pageable pageable);
    DisbursementResponse updateDisbursementStatus(Long id, Disbursement.DisbursementStatus status);
}
