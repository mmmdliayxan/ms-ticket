package com.example.msticket.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketOrderRequest {
    private Long ticketId;
    private String userPhone;
    private Integer quantity;
}