package com.example.msticket.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketRequest {
    private Long eventId;
    private String type;
    private Double price;
    private Integer availableQuantity;
}

