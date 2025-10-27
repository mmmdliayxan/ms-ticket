package com.example.msticket.client;

import com.example.msticket.dto.request.PaymentRequestDto;
import com.example.msticket.model.TicketOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "ms-payment-orchestrator", url = "http://localhost:8080")
public interface PaymentOrchestratorClient {

    @PostMapping("/api/saga/start")
    void startSaga(TicketOrder order, PaymentRequestDto paymentRequestDto);
}