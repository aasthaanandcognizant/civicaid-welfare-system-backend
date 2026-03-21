package com.cognizant.civicaid.service.implementation;

import com.cognizant.civicaid.dto.request.DisbursementRequest;
import com.cognizant.civicaid.dto.response.DisbursementResponse;
import com.cognizant.civicaid.entity.*;
import com.cognizant.civicaid.exception.BusinessException;
import com.cognizant.civicaid.exception.ResourceNotFoundException;
import com.cognizant.civicaid.repository.DisbursementRepository;
import com.cognizant.civicaid.repository.WelfareApplicationRepository;
import com.cognizant.civicaid.service.DisbursementService;
import com.cognizant.civicaid.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DisbursementServiceImpl implements DisbursementService {

    private final DisbursementRepository disbursementRepository;
    private final WelfareApplicationRepository applicationRepository;
    private final NotificationService notificationService;

    @Override
    @Transactional
    public DisbursementResponse createDisbursement(DisbursementRequest request) {
        WelfareApplication application = applicationRepository.findById(request.getApplicationId())
                .orElseThrow(() -> new ResourceNotFoundException("Application", request.getApplicationId()));

        if (application.getStatus() != WelfareApplication.ApplicationStatus.APPROVED) {
            throw new BusinessException("Disbursement can only be created for APPROVED applications");
        }

        Disbursement disbursement = Disbursement.builder()
                .application(application)
                .amount(request.getAmount())
                .remarks(request.getRemarks())
                .status(Disbursement.DisbursementStatus.PENDING)
                .build();

        disbursement = disbursementRepository.save(disbursement);

        application.setStatus(WelfareApplication.ApplicationStatus.DISBURSED);
        applicationRepository.save(application);

        notificationService.sendNotification(
                application.getCitizen().getUser().getUserId(),
                disbursement.getDisbursementId(),
                "A disbursement of ₹" + request.getAmount() + " has been initiated for your application.",
                Notification.NotificationCategory.DISBURSEMENT
        );

        return mapToResponse(disbursement);
    }

    @Override
    public DisbursementResponse getDisbursementById(Long id) {
        return mapToResponse(disbursementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disbursement", id)));
    }

    @Override
    public List<DisbursementResponse> getDisbursementsByApplication(Long applicationId) {
        return disbursementRepository.findByApplication_ApplicationId(applicationId)
                .stream().map(this::mapToResponse).toList();
    }

    @Override
    public Page<DisbursementResponse> getDisbursementsByStatus(Disbursement.DisbursementStatus status, Pageable pageable) {
        return disbursementRepository.findByStatus(status, pageable).map(this::mapToResponse);
    }

    @Override
    public Page<DisbursementResponse> getAllDisbursements(Pageable pageable) {
        return disbursementRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    @Transactional
    public DisbursementResponse updateDisbursementStatus(Long id, Disbursement.DisbursementStatus status) {
        Disbursement disbursement = disbursementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disbursement", id));
        disbursement.setStatus(status);
        disbursement = disbursementRepository.save(disbursement);

        notificationService.sendNotification(
                disbursement.getApplication().getCitizen().getUser().getUserId(),
                disbursement.getDisbursementId(),
                "Your disbursement status has been updated to: " + status.name(),
                Notification.NotificationCategory.DISBURSEMENT
        );

        return mapToResponse(disbursement);
    }

    private DisbursementResponse mapToResponse(Disbursement d) {
        return DisbursementResponse.builder()
                .disbursementId(d.getDisbursementId())
                .applicationId(d.getApplication().getApplicationId())
                .citizenName(d.getApplication().getCitizen().getName())
                .programTitle(d.getApplication().getProgram().getTitle())
                .amount(d.getAmount())
                .date(d.getDate())
                .status(d.getStatus())
                .remarks(d.getRemarks())
                .updatedAt(d.getUpdatedAt())
                .build();
    }
}
