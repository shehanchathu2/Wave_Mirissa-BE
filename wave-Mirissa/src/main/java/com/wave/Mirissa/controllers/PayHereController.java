package com.wave.Mirissa.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payhere")
@CrossOrigin(origins = "*")
public class PayHereController {

    private static final String MERCHANT_ID = "1231047";
    private static final String MERCHANT_SECRET_BASE64 = "MzUwODYyMjE3OTIyNzI2NDg3NTIxNDEwMTM5NTk1MjAxMzgwNjM3Ng==";
    private static final String CURRENCY = "LKR";

    @GetMapping("/hash")
    public ResponseEntity<Map<String, String>> getHash(
            @RequestParam String orderId,
            @RequestParam String amount) {

        try {
            String merchantSecret = new String(
                    Base64.getDecoder().decode(MERCHANT_SECRET_BASE64),
                    StandardCharsets.UTF_8
            );

            double parsedAmount = Double.parseDouble(amount);
            if (parsedAmount <= 0) {
                throw new IllegalArgumentException("Amount must be positive");
            }

            String hash = generateHash(MERCHANT_ID, orderId, parsedAmount, CURRENCY, merchantSecret);

            System.out.println("Generating hash with parameters:");
            System.out.println("Merchant ID: " + MERCHANT_ID);
            System.out.println("Order ID: " + orderId);
            System.out.println("Amount: " + String.format("%.2f", parsedAmount));
            System.out.println("Currency: " + CURRENCY);
            System.out.println("Concatenated String: " + MERCHANT_ID + orderId + String.format("%.2f", parsedAmount) + CURRENCY + merchantSecret);
            System.out.println("Generated Hash: " + hash);

            Map<String, String> response = new HashMap<>();
            response.put("hash", hash);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Error generating hash: " + e.getMessage());
            throw new RuntimeException("Failed to generate payment hash", e);
        }
    }

    private String generateHash(String merchantId, String orderId, double amount, String currency, String merchantSecret) {
        String formattedAmount = String.format("%.2f", amount);
        String data = merchantId + orderId + formattedAmount + currency + merchantSecret;
        return md5(data).toUpperCase();
    }

    private String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 hashing failed", e);
        }
    }
}