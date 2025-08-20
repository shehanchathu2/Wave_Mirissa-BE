package com.wave.Mirissa.controllers;

import com.wave.Mirissa.dtos.OrderDTO;
import com.wave.Mirissa.dtos.OrderDetailedDTO;
import com.wave.Mirissa.models.Order;
import com.wave.Mirissa.models.OrderStatus;
import com.wave.Mirissa.repositories.OrderRepository;
import com.wave.Mirissa.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminOrderController {
    private final OrderService orderService;
    private final OrderRepository orderRepository;




    public AdminOrderController(OrderService orderService, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        // Why: admins may create orders on behalf of a user
        OrderDTO created = orderService.createOrder(orderDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/paid")
    public ResponseEntity<List<OrderDetailedDTO>> getPaidOrdersForAdmin(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        List<OrderDetailedDTO> orders = orderService.getPaidOrdersForAdmin(page, size);
        return ResponseEntity.ok(orders);
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setOrderStatus(status); // use camelCase setter
        orderRepository.save(order);

        return ResponseEntity.ok(order);
    }


}