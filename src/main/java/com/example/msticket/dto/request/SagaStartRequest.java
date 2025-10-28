package com.example.msticket.dto.request;

import com.example.msticket.model.TicketOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SagaStartRequest {
    private TicketOrder order;
    private PaymentRequestDto paymentRequest;
}