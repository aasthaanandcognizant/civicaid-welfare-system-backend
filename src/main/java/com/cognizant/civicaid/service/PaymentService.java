package com.cognizant.civicaid.service;

import com.cognizant.civicaid.dto.request.PaymentRequest;
import com.cognizant.civicaid.dto.response.PaymentResponse;
import com.cognizant.civicaid.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PaymentService {
    PaymentResponse initiatePayment(PaymentRequest request);
    PaymentResponse getPaymentById(Long id);
    List<PaymentResponse> getPaymentsByDisbursement(Long disbursementId);
    Page<PaymentResponse> getAllPayments(Pageable pageable);
    PaymentResponse updatePaymentStatus(Long id, Payment.PaymentStatus status);
}
