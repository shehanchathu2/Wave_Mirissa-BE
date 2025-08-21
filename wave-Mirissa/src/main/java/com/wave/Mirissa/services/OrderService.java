package com.wave.Mirissa.services;

import com.wave.Mirissa.dtos.OrderDTO;
import com.wave.Mirissa.dtos.OrderDetailedDTO;
import com.wave.Mirissa.models.*;
import com.wave.Mirissa.repositories.OrderItemRepository;
import com.wave.Mirissa.repositories.OrderRepository;
import com.wave.Mirissa.repositories.ProductRepository;
import com.wave.Mirissa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public OrderDTO createOrder(OrderDTO orderDTO) {
        System.out.println("createOrder called with orderId: " + orderDTO.getOrderId());
        System.out.println("Received productIds: " + orderDTO.getProductIds());

        Order order = new Order();
        order.setOrderId(orderDTO.getOrderId());
        order.setAmount(new BigDecimal(orderDTO.getAmount()));
        order.setCurrency(orderDTO.getCurrency());
        order.setStatus(orderDTO.getStatus());
        order.setPaymentMethod(orderDTO.getPaymentMethod());
        order.setPayhereRef(orderDTO.getPayhereRef());

        if (orderDTO.getUserId() != null) {
            User user = userRepository.findById(orderDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            order.setUser(user);
        }

        if (orderDTO.getProductIds() != null && !orderDTO.getProductIds().isEmpty()) {
            List<Products> productList = productRepository.findAllById(orderDTO.getProductIds());






            String productNames = productList.stream()
                    .map(Products::getName)
                    .collect(Collectors.joining(","));
            order.setProductNames(productNames);

            String customizationSummary = productList.stream()
                    .map(p -> {
                        String customizationDetails = (p.getCustomizations() != null && !p.getCustomizations().isEmpty())
                                ? p.getCustomizations().stream()
                                .map(Customization::getName)
                                .collect(Collectors.joining(","))
                                : "No customization";
                        return p.getName() + ": " + customizationDetails;
                    })
                    .collect(Collectors.joining("|"));
            order.setCustomizationSummary(customizationSummary);

            System.out.println("----- Tracing Order Before Save -----");
            System.out.println("Order ID: " + order.getOrderId());
            System.out.println("Product Names: " + order.getProductNames());
            System.out.println("Customization Summary: " + order.getCustomizationSummary());
            System.out.println("--------------------------------------");

            Order tempSavedOrder = orderRepository.save(order);
            tempSavedOrder.setProducts(productList);

            orderRepository.flush();
            Order savedOrder = orderRepository.findById(tempSavedOrder.getId()).orElseThrow();


            System.out.println("Order Saved with ID: " + savedOrder.getId());

            productList.forEach(product -> {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(savedOrder);
                orderItem.setProducts(product);
                orderItem.setPriceSnapshot(product.getPrice());
                if (product.getCustomizations() != null && !product.getCustomizations().isEmpty()) {
                    orderItem.setSelectCustomization(new ArrayList<>(product.getCustomizations()));

                }
                orderItemRepository.save(orderItem);
            });
            return mapToDTO(savedOrder);
        } else {
            Order savedOrder = orderRepository.save(order);
            return mapToDTO(savedOrder);
        }
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

    public OrderDetailedDTO mapToDetailedDTO(Order order) {
        OrderDetailedDTO dto = new OrderDetailedDTO();
        dto.setId(order.getId());
        dto.setOrderId(order.getOrderId());
        dto.setAmount(order.getAmount());
        dto.setCurrency(order.getCurrency());
        dto.setStatus(order.getStatus());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setPayhereRef(order.getPayhereRef());
        dto.setCreatedAt(order.getCreatedAt().toString());

        if (order.getUser() != null) {
            OrderDetailedDTO.UserDTO userDTO = new OrderDetailedDTO.UserDTO();
            userDTO.setId(order.getUser().getId());
            userDTO.setName(order.getUser().getUsername());
            userDTO.setEmail(order.getUser().getEmail());
            dto.setUser(userDTO);
        }

        if (order.getOrderItems() != null) {
            List<OrderDetailedDTO.ProductWithCustomizationDTO> products = order.getOrderItems().stream().map(orderItem -> {
                OrderDetailedDTO.ProductWithCustomizationDTO pDto = new OrderDetailedDTO.ProductWithCustomizationDTO();
                pDto.setProductId(orderItem.getProducts().getProduct_id());
                pDto.setName(orderItem.getProducts().getName());
                pDto.setPrice(orderItem.getProducts().getPrice());
                if (orderItem.getSelectCustomization() != null) {
                    pDto.setCustomizations(orderItem.getSelectCustomization().stream()
                            .map(Customization::getName)
                            .collect(Collectors.toList()));
                }
                return pDto;
            }).collect(Collectors.toList());
            dto.setProducts(products);
        }
        return dto;
    }

    public List<OrderDetailedDTO> getPaidOrdersForAdmin()
    {
        List<Order> orders = orderRepository.findAllByStatus("PAID");
        return orders.stream().map(this::mapToDetailedDTO).toList();
    }



}