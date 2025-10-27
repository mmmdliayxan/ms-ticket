package com.example.msticket.client;


import com.example.msticket.dto.response.EventDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-event", url = "http://localhost:8080")
public interface EventClient {

    @GetMapping("/api/events/{id}")
    EventDto getEventById(@PathVariable Long id);
}
