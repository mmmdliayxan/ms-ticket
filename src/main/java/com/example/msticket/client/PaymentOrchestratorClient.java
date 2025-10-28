package com.example.msticket.client;

import com.example.msticket.dto.request.SagaStartRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-payment-orchestrator", url = "http://ms-gateway:8080")
public interface PaymentOrchestratorClient {

    @PostMapping("/api/saga/start")
    void startSaga(@RequestBody SagaStartRequest request);
}