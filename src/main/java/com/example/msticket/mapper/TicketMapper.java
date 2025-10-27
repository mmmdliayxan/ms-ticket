package com.example.msticket.mapper;

import com.example.msticket.dto.request.TicketOrderRequest;
import com.example.msticket.dto.request.TicketRequest;
import com.example.msticket.dto.response.TicketOrderResponse;
import com.example.msticket.dto.response.TicketResponse;
import com.example.msticket.model.Ticket;
import com.example.msticket.model.TicketOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    Ticket toEntity(TicketRequest dto);

    TicketResponse toDto(Ticket entity);

    TicketOrder toEntity(TicketOrderRequest dto);

    TicketOrderResponse toDto(TicketOrder entity);
}