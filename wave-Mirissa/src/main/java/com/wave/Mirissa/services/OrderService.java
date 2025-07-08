package com.wave.Mirissa.services;

import com.wave.Mirissa.dtos.OrderDTO;
import com.wave.Mirissa.models.Order;
import com.wave.Mirissa.models.Products;
import com.wave.Mirissa.models.User;
import com.wave.Mirissa.repositories.OrderRepository;
import com.wave.Mirissa.repositories.ProductRepository;
import com.wave.Mirissa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public OrderDTO createOrder (OrderDTO orderDTO){
        Order order = new Order();
        order.setOrderId(orderDTO.getOrderId());
        order.setAmount(new BigDecimal(orderDTO.getAmount()));
        order.setCurrency(orderDTO.getCurrency());
        order.setStatus(orderDTO.getStatus());
        order.setPaymentMethod(orderDTO.getPaymentMethod());
        order.setPayhereRef(orderDTO.getPayhereRef());

        if(orderDTO.getUserId() != null){
            User user =  userRepository.findById(orderDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            order.setUser(user);

        }

        if(orderDTO.getProductIds() != null){
            List<Products> products = productRepository.findAllById(orderDTO.getProductIds());
            order.setProducts(products);
        }

        Order saveOrder = orderRepository.save(order);
        return mapToDTO(saveOrder);

    }



    private OrderDTO mapToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderId(order.getOrderId());
        dto.setAmount(order.getAmount().toPlainString());
        dto.setCurrency(order.getCurrency());
        dto.setStatus(order.getStatus());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setPayhereRef(order.getPayhereRef());
        dto.setUserId(order.getUser() != null ? order.getUser().getId() : null);
        dto.setProductIds(order.getProducts() != null ?
                order.getProducts().stream().map(Products::getProduct_id).toList() : null);
        return dto;
    }

}
