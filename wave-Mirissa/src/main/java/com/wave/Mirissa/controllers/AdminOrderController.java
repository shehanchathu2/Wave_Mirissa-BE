package com.wave.Mirissa.controllers;

import com.wave.Mirissa.dtos.OrderDTO;
import com.wave.Mirissa.models.Order;
import com.wave.Mirissa.models.Products;
import com.wave.Mirissa.repositories.OrderRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
@CrossOrigin(origins = "*")
public class AdminOrderController {

    private OrderRepository orderRepository;

    public AdminOrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/{id}")
    public OrderDTO getOrder(@PathVariable Long id) {
        return orderRepository.findById(id).map(this::mapTODTO)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }


    private OrderDTO mapTODTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderId(order.getOrderId());
        dto.setAmount(order.getAmount().toPlainString());
        dto.setCurrency(order.getCurrency());
        dto.setStatus(order.getStatus());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setUserId(order.getUser() != null ? order.getUser().getId() : null);
        dto.setProductIds(
                order.getProducts() != null ?
                        order.getProducts().stream().map(Products::getProduct_id).toList() :
                        null
        );
        dto.setPayhereRef(order.getPayhereRef());
        return dto;
    }


    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(this::mapTODTO).toList();

    }

}
