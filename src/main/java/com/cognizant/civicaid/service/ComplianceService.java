package com.cognizant.civicaid.service;

import com.cognizant.civicaid.dto.request.ComplianceRecordRequest;
import com.cognizant.civicaid.dto.response.ComplianceRecordResponse;
import com.cognizant.civicaid.entity.ComplianceRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ComplianceService {
    ComplianceRecordResponse createRecord(Long reviewerId, ComplianceRecordRequest request);
    ComplianceRecordResponse getRecordById(Long id);
    List<ComplianceRecordResponse> getRecordsByEntity(Long entityId, ComplianceRecord.EntityType type);
    Page<ComplianceRecordResponse> getAllRecords(Pageable pageable);
    Page<ComplianceRecordResponse> getRecordsByResult(ComplianceRecord.ComplianceResult result, Pageable pageable);
    ComplianceRecordResponse updateRecord(Long id, ComplianceRecordRequest request);
}
