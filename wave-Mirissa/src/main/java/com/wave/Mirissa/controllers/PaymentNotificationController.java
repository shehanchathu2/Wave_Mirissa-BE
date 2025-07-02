package com.wave.Mirissa.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentNotificationController {

    @PostMapping("/notify")
    public ResponseEntity<String> handlePayHereNotification(@RequestParam Map<String, String> params) {
        System.out.println("Payment Notification received:");
        params.forEach((k, v) -> System.out.println(k + ": " + v));
        return ResponseEntity.ok("Notification received");
    }
}