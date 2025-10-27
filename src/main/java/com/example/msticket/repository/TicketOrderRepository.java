package com.example.msticket.repository;


import com.example.msticket.model.TicketOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketOrderRepository extends JpaRepository<TicketOrder, Long> {
    List<TicketOrder> findByUserId(Long userId);
}
