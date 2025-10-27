package com.example.msticket.service;

import com.example.msticket.client.EventClient;
import com.example.msticket.client.PaymentOrchestratorClient;
import com.example.msticket.dto.request.PaymentRequestDto;
import com.example.msticket.dto.request.TicketOrderRequest;
import com.example.msticket.dto.request.TicketRequest;
import com.example.msticket.dto.response.TicketOrderResponse;
import com.example.msticket.dto.response.TicketResponse;
import com.example.msticket.mapper.TicketMapper;
import com.example.msticket.model.Ticket;
import com.example.msticket.model.TicketOrder;
import com.example.msticket.model.enums.TicketOrderStatus;
import com.example.msticket.repository.TicketOrderRepository;
import com.example.msticket.repository.TicketRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketOrderService {

    private final TicketRepository ticketRepository;
    private final TicketOrderRepository orderRepository;
    private final TicketMapper mapper;
    private final EventClient eventClient;
    private final PaymentOrchestratorClient orchestratorClient;

    @Transactional
    public TicketResponse createTicket(TicketRequest request) {
        log.info("Creating ticket for eventId={}", request.getEventId());

        try {
            eventClient.getEventById(request.getEventId());
        } catch (FeignException.NotFound e) {
            log.error("Event {} not found", request.getEventId());
            throw new IllegalArgumentException("Event not found: " + request.getEventId());
        }

        Ticket ticket = mapper.toEntity(request);
        ticketRepository.save(ticket);

        log.info("Ticket created with id={} for eventId={}", ticket.getId(), request.getEventId());
        return mapper.toDto(ticket);
    }

    public List<TicketResponse> getTicketsByEvent(Long eventId) {
        log.info("Fetching tickets for eventId={}", eventId);
        List<TicketResponse> tickets = ticketRepository.findByEventId(eventId)
                .stream().map(mapper::toDto).collect(Collectors.toList());
        log.info("{} tickets found for eventId={}", tickets.size(), eventId);
        return tickets;
    }

    @Transactional
    public TicketOrderResponse orderTicket(TicketOrderRequest request, Long userId, PaymentRequestDto paymentRequestDto) {
        log.info("User {} ordering ticketId={} quantity={}", userId, request.getTicketId(), request.getQuantity());

        Ticket ticket = ticketRepository.findById(request.getTicketId())
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));

        if (ticket.getAvailableQuantity() < request.getQuantity()) {
            log.warn("Not enough tickets available for ticketId={} requested={} available={}", ticket.getId(),
                    request.getQuantity(), ticket.getAvailableQuantity());
            throw new IllegalArgumentException("Not enough tickets available");
        }

        TicketOrder order = mapper.toEntity(request);
        order.setUserId(userId);
        order.setStatus(TicketOrderStatus.PENDING);
        orderRepository.save(order);

        orchestratorClient.startSaga(order,paymentRequestDto);
        log.info("Order created with id={} for user {}", order.getId(), userId);

        return mapper.toDto(order);
    }

    public List<TicketOrderResponse> getOrdersByUser(Long userId) {
        log.info("Fetching orders for user {}", userId);
        List<TicketOrderResponse> orders = orderRepository.findByUserId(userId)
                .stream().map(mapper::toDto).collect(Collectors.toList());
        log.info("{} orders found for user {}", orders.size(), userId);
        return orders;
    }

    @Transactional
    public void cancelOrder(Long orderId, Long userId) {
        log.info("User {} cancelling order {}", userId, orderId);

        TicketOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if (!order.getUserId().equals(userId)) {
            log.warn("User {} not authorized to cancel order {}", userId, orderId);
            throw new SecurityException("Not authorized to cancel this order");
        }

        order.setStatus(TicketOrderStatus.CANCELLED);
        orderRepository.save(order);

        log.info("Order {} cancelled by user {}", orderId, userId);
    }

    @Transactional
    public void updateStatus(Long orderId, String status) {
        TicketOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.setStatus(TicketOrderStatus.valueOf(status));
        orderRepository.save(order);

        log.info("Order {} status updated to {}", orderId, status);
    }
}
