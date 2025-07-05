package com.wave.Mirissa.controllers;

import com.wave.Mirissa.dtos.PaymentDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payhere")
@CrossOrigin(origins = "*")
public class PaymentController {
    private static final String MERCHANT_ID = "1231066";
    private static final String MERCHANT_SECRET = "MTQzMTYwMTYwODcxNzY4NjU0NDIxNDQwMzY3OTAyODI0NDc3Mjg4";

    @GetMapping("/hash")
    public PaymentDTO genaratePayHereHash(@RequestParam("amount") double amount){
        try{
            String orderId = "ORDER_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 1000);
            DecimalFormat df = new DecimalFormat("0.00");
            String FormattedAmount = df.format(amount);
            String Currency = "LKR";

            String merchantSecretHash = getMd5(MERCHANT_SECRET).toUpperCase();
            String dataToHash = MERCHANT_ID + orderId + FormattedAmount + Currency + merchantSecretHash;
            String hash = getMd5(dataToHash).toUpperCase();

//            Map<String ,String> responce = new HashMap<>();
//            responce.put("order_id",orderId);
//            responce.put("amount" ,FormattedAmount);
//            responce.put("currency",Currency);
//            responce.put("hash",hash);


            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setOrderId(orderId);
            paymentDTO.setHash(hash);
            paymentDTO.setAmount(FormattedAmount);



            System.out.println("Merchant ID: " + MERCHANT_ID);
            System.out.println("Order ID: " + orderId);
            System.out.println("Formatted Amount: " + FormattedAmount);
            System.out.println("Currency: " + Currency);
            System.out.println("Merchant Secret Hash: " + merchantSecretHash);
            System.out.println("String to Hash: " + dataToHash);
            System.out.println("Generated Hash: " + hash);

            return paymentDTO;

        } catch (Exception e) {
            throw new RuntimeException("Error generating PayHere hash",e);
        }
    }


    @PostMapping("/notify")
    public ResponseEntity<String> handlePaymentNotification(@RequestBody Map<String ,String> payload){
        String receivedMerchantId = payload.get("merchant_id");
        String orderId = payload.get("order_id");
        String payHereAmount = payload.get("payhere_amount");
        String payhereCurrency = payload.get("payhere_currency");
        String statusCode = payload.get("status_code");
        String md5sig  = payload.get("md5sig");

        String localMd5sig = getMd5(receivedMerchantId + orderId + payHereAmount + payhereCurrency + statusCode + getMd5(MERCHANT_SECRET));
        if(localMd5sig.equalsIgnoreCase(md5sig) && "2".equals(statusCode)){
            return new ResponseEntity<>("payment sucess", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Payment verification failed for order:",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private String getMd5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext.toUpperCase();
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
