package com.example.msticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication

@EnableFeignClients(basePackages = "com.example.msticket.client")
public class MsTicketApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsTicketApplication.class, args);
    }

}
