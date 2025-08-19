package com.wave.Mirissa.controllers;

import com.wave.Mirissa.dtos.OrderDTO;
import com.wave.Mirissa.dtos.OrderDetailedDTO;
import com.wave.Mirissa.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class UserOrderController {

    private final OrderService orderService;

    public UserOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        OrderDTO createdOrder = orderService.createOrder(orderDTO);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping("/paid")
    public ResponseEntity<List<OrderDetailedDTO>> getPaidOrdersForAdmin(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        List<OrderDetailedDTO> orders = orderService.getPaidOrdersForAdmin(page, size);
        return ResponseEntity.ok(orders);
    }
}