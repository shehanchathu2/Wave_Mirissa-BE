package com.wave.Mirissa.services;

import com.wave.Mirissa.dtos.OrderDTO;
import com.wave.Mirissa.dtos.OrderDetailedDTO;
import com.wave.Mirissa.models.*;
import com.wave.Mirissa.repositories.OrderItemRepository;
import com.wave.Mirissa.repositories.OrderRepository;
import com.wave.Mirissa.repositories.ProductRepository;
import com.wave.Mirissa.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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


    @Transactional
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

        // Set user if exists
        if (orderDTO.getUserId() != null) {
            User user = userRepository.findById(orderDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + orderDTO.getUserId()));
            order.setUser(user);
        }

        // Process products
        if (orderDTO.getProductIds() != null && !orderDTO.getProductIds().isEmpty()) {
            List<Products> productList = productRepository.findAllById(orderDTO.getProductIds());

            // Find missing product IDs
            List<Long> foundIds = productList.stream()
                    .map(Products::getProduct_id)
                    .toList();
            List<Long> missingIds = orderDTO.getProductIds().stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();

            if (!missingIds.isEmpty()) {
                System.out.println("Warning: Some product IDs not found: " + missingIds);
                // Optionally throw or continue with existing products
                // throw new RuntimeException("No products found for IDs: " + missingIds);
            }

            if (!productList.isEmpty()) {
                // Snapshot product names
                order.setProductNames(productList.stream()
                        .map(Products::getName)
                        .collect(Collectors.joining(",")));

                // Snapshot customization summary
                order.setCustomizationSummary(productList.stream()
                        .map(p -> {
                            String customizationDetails = (p.getCustomizations() != null && !p.getCustomizations().isEmpty())
                                    ? p.getCustomizations().stream()
                                    .map(Customization::getName)
                                    .collect(Collectors.joining(","))
                                    : "No customization";
                            return p.getName() + ": " + customizationDetails;
                        })
                        .collect(Collectors.joining("|")));

                // Build order items
                List<OrderItem> orderItems = new ArrayList<>();
                for (Products product : productList) {
                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setProducts(product);
                    item.setProductNameSnapshot(product.getName());
                    item.setPriceSnapshot(product.getPrice());

                    if (product.getCustomizations() != null && !product.getCustomizations().isEmpty()) {
                        item.setSelectCustomization(new ArrayList<>(product.getCustomizations()));
                    }

                    orderItems.add(item);
                }
                order.setOrderItems(orderItems);
            }
        }

        Order savedOrder = orderRepository.save(order);
        return mapToDTO(savedOrder);
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

    public List<OrderDetailedDTO> getPaidOrdersForAdmin(Integer page, Integer size) {
        if (page != null && size != null) {
            Pageable pageable = PageRequest.of(page, size);
            List<Order> orders = orderRepository.findAllByStatusWithDetails("PAID", pageable);
            return orders.stream()
                    .map(this::mapToDetailedDTO)
                    .toList();
        } else {
            List<Order> orders = orderRepository.findAllByStatusWithDetails("PAID");
            return orders.stream()
                    .map(this::mapToDetailedDTO)
                    .toList();
        }
    }





}