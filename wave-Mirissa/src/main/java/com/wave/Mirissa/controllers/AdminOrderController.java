package com.wave.Mirissa.controllers;

import com.wave.Mirissa.dtos.OrderDTO;
import com.wave.Mirissa.dtos.OrderDetailedDTO;
import com.wave.Mirissa.models.Order;
import com.wave.Mirissa.models.Products;
import com.wave.Mirissa.models.User;
import com.wave.Mirissa.repositories.OrderRepository;
import com.wave.Mirissa.repositories.ProductRepository;
import com.wave.Mirissa.repositories.UserRepository;
import com.wave.Mirissa.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
@CrossOrigin(origins = "*")
public class AdminOrderController {

 private OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO){
        OrderDTO createOrder = orderService.createOrder(orderDTO);

        return new ResponseEntity<>(createOrder,HttpStatus.CREATED);
    }


    @GetMapping("/paid")
    public ResponseEntity<List<OrderDetailedDTO>> getPaidOrdersForAdmin() {
        List<OrderDetailedDTO> orders = orderService.getPaidOrdersForAdmin();
        return ResponseEntity.ok(orders);
    }

}
