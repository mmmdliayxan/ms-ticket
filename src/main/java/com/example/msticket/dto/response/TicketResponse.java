package com.example.msticket.dto.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketResponse {
    private Long id;
    private Long eventId;
    private String type;
    private Double price;
    private Integer availableQuantity;
}
