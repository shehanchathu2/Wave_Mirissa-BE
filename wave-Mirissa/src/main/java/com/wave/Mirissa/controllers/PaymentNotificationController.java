package com.wave.Mirissa.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentNotificationController {

    @PostMapping("/notify")
    public ResponseEntity<String> handlePayHereNotification(@RequestParam Map<String, String> params) {
        System.out.println("Payment Notification received:");
        params.forEach((key, value) -> System.out.println(key + ": " + value));

        // Optional: Validate signature and update DB
        return ResponseEntity.ok("Notification received successfully.");
    }
}