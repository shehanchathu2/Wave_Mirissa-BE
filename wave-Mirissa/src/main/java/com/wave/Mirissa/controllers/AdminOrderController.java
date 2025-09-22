package com.wave.Mirissa.controllers;

import com.wave.Mirissa.dtos.*;
import com.wave.Mirissa.models.Order;
import com.wave.Mirissa.models.OrderStatus;
import com.wave.Mirissa.repositories.OrderRepository;
import com.wave.Mirissa.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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


    @PostMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status
    ) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setOrderStatus(status);
        orderRepository.save(order);
        return ResponseEntity.ok(order);
    }


    @PutMapping("/{id}/tracking")
    public ResponseEntity<Order> updateTracking(
            @PathVariable Long id,
            @RequestBody TrackingRequest request
    ) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Order order = optionalOrder.get();
        order.setTrackingNumber(request.getTrackingNumber());
        order.setEstimateDate(request.getEstimateDate());

        Order saved = orderRepository.save(order);
        return ResponseEntity.ok(saved);
    }

//    @PatchMapping("/{orderId}/status")
//    public ResponseEntity<Order> updateOrderStatus(
//            @PathVariable Long orderId,
//            @RequestParam OrderStatus status) {
//
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new RuntimeException("Order not found"));
//
//        order.setOrderStatus(status); // use camelCase setter
//        orderRepository.save(order);
//
//        return ResponseEntity.ok(order);
//    }

    @GetMapping("/revenue-trends")
    public ResponseEntity<?> getRevenueTrends() {
        List<Order> allOrders = orderRepository.findAll();

        System.out.println(allOrders);
        BigDecimal totalAmount = allOrders.stream()
                .map(Order::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long totalOrders = allOrders.size();

        Map<String, Long> statusCounts = allOrders.stream()
                .collect(Collectors.groupingBy(
                        o -> o.getOrderStatus() != null ? o.getOrderStatus().name() : "UNKNOWN",
                        Collectors.counting()
                ));

        Map<String, Object> response = Map.of(
                "totalAmount", totalAmount,
                "totalOrders", totalOrders,
                "orderStatusCounts", statusCounts
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/monthly-revenue")
    public List<MonthlyRevenueDTO> getMonthlyRevenue() {
        return orderService.getMonthlyRevenue();
    }

}