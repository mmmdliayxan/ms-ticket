package com.example.msticket.controller;

import com.example.msticket.dto.request.PaymentRequestDto;
import com.example.msticket.dto.request.TicketOrderRequest;
import com.example.msticket.dto.request.TicketRequest;
import com.example.msticket.dto.response.TicketOrderResponse;
import com.example.msticket.dto.response.TicketResponse;
import com.example.msticket.service.TicketOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketOrderController {

    private final TicketOrderService service;

    @PostMapping
    @PreAuthorize("hasRole('ORGANIZER') or hasRole('ADMIN')")
    public ResponseEntity<TicketResponse> createTicket(@Valid @RequestBody TicketRequest request) {
        return ResponseEntity.ok(service.createTicket(request));
    }

    @GetMapping("/event/{eventId}")
    @PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN', 'USER')")
    public ResponseEntity<List<TicketResponse>> getTicketsByEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(service.getTicketsByEvent(eventId));
    }

    // ---------------- User APIs ----------------
    @PostMapping("/order")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TicketOrderResponse> orderTicket(
            @Valid @RequestBody TicketOrderRequest request,
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody PaymentRequestDto paymentRequestDto) {
        return ResponseEntity.ok(service.orderTicket(request, userId,paymentRequestDto));
    }

    @GetMapping("/orders")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<TicketOrderResponse>> getMyOrders(@RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(service.getOrdersByUser(userId));
    }

    @PostMapping("/order/{orderId}/cancel")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> cancelOrder(
            @PathVariable Long orderId,
            @RequestHeader("X-User-Id") Long userId) {
        service.cancelOrder(orderId, userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{orderId}/status/{status}")
    public ResponseEntity<Void> updateOrderStatus(
            @PathVariable Long orderId,
            @PathVariable String status) {
        service.updateStatus(orderId, status);
        return ResponseEntity.ok().build();
    }
}
