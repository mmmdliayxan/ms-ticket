package com.example.msticket.dto.response;

import com.example.msticket.model.enums.TicketOrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class TicketOrderResponse {
    private Long id;
    private Long ticketId;
    private Long userId;
    private String userPhone;
    private Integer quantity;
    private TicketOrderStatus status;
}