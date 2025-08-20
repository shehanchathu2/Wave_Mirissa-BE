package com.wave.Mirissa.controllers;

import com.wave.Mirissa.dtos.OrderDTO;
import com.wave.Mirissa.dtos.OrderDetailedDTO;
import com.wave.Mirissa.dtos.OrderItemResponseDTO;
import com.wave.Mirissa.dtos.OrderResponseDTO;
import com.wave.Mirissa.models.Order;
import com.wave.Mirissa.repositories.OrderRepository;
import com.wave.Mirissa.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/my-orders/{userId}")
    public List<OrderResponseDTO> getMyOrders(@PathVariable Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);

        return orders.stream().map(order -> {
            OrderResponseDTO dto = new OrderResponseDTO();
            dto.setOrderId(order.getOrderId());
            dto.setOrderStatus(order.getOrderStatus());
            dto.setPaymentMethod(order.getPaymentMethod());
            dto.setAmount(order.getAmount());

            List<OrderItemResponseDTO> items = order.getOrderItems().stream().map(item -> {
                OrderItemResponseDTO itemDTO = new OrderItemResponseDTO();
                itemDTO.setProductName(item.getProductNameSnapshot());
                itemDTO.setPrice(item.getPriceSnapshot());
                itemDTO.setQuantity(1); // if you have quantity, add in model

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
}