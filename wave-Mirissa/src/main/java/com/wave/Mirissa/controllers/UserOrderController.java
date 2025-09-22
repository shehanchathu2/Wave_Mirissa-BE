package com.wave.Mirissa.controllers;

import com.wave.Mirissa.dtos.*;
import com.wave.Mirissa.models.Order;
import com.wave.Mirissa.repositories.OrderRepository;
import com.wave.Mirissa.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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


    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/my-orders/{userId}")
    public List<OrderResponseDTO> getMyOrders(@PathVariable Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);

        return orders.stream().map(order -> {
            OrderResponseDTO dto = new OrderResponseDTO();
            dto.setOrder_id(order.getId());
            dto.setOrderId(order.getOrderId());
            dto.setOrderStatus(order.getOrderStatus());
            dto.setPaymentMethod(order.getPaymentMethod());
            dto.setAmount(order.getAmount());

            // Set tracking info
            dto.setTrackingNumber(order.getTrackingNumber());
            dto.setEstimateDate(order.getEstimateDate());

            dto.setCreatedAt(java.sql.Timestamp.valueOf(order.getCreatedAt()));

            List<OrderItemResponseDTO> items = order.getOrderItems().stream().map(item -> {
                OrderItemResponseDTO itemDTO = new OrderItemResponseDTO();
                itemDTO.setProductName(item.getProductNameSnapshot());
                itemDTO.setPrice(item.getPriceSnapshot());
                itemDTO.setQuantity(1); // if you have quantity, add in model
                itemDTO.setProduct_id(item.getProducts().getProduct_id());
                itemDTO.setCustomizations(
                        item.getSelectCustomization().stream().map(c -> c.getName()).toList()
                );
                itemDTO.setProductImageUrl(item.getProductImageUrlSnapshot());
                return itemDTO;
            }).toList();

            dto.setItems(items);
            return dto;
        }).toList();
    }




    @GetMapping("/{id}/tracking")
    public ResponseEntity<?> getTrackingInfo(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(order -> ResponseEntity.ok(
                        new TrackingInfoDTO(order.getTrackingNumber(), order.getEstimateDate())
                ))
                .orElse(ResponseEntity.notFound().build());
    }
}