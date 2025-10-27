package com.example.msticket.model;

import com.example.msticket.model.enums.TicketOrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ticket_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ticketId;
    private Long userId;
    private String userPhone;
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private TicketOrderStatus status; // PENDING, CONFIRMED, FAILED, CANCELLED
}
