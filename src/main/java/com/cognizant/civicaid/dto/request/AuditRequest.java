package com.cognizant.civicaid.dto.request;

import com.cognizant.civicaid.entity.Audit;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuditRequest {
    @NotBlank
    private String scope;
    private String findings;
    private Audit.AuditStatus status;
}
