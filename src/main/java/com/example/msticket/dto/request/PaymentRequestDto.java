package com.example.msticket.dto.request;

import lombok.Data;

@Data
public class PaymentRequestDto {
    private Long userId;
    private Double amount;
    private String cardNumber;
}
