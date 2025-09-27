package com.wave.Mirissa.services;

import com.wave.Mirissa.dtos.ReviewResponseDTO;
import com.wave.Mirissa.models.*;
import com.wave.Mirissa.repositories.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productsRepository;
    private final UserRepository userRepository; // assuming you have this
    private final OrderRepository orderRepository;



    public ReviewService(ReviewRepository reviewRepository, OrderItemRepository orderItemRepository,
                         ProductRepository productsRepository, UserRepository userRepository, OrderRepository orderRepository) {
        this.reviewRepository = reviewRepository;
        this.orderItemRepository = orderItemRepository;
        this.productsRepository = productsRepository;
        this.userRepository = userRepository;

        this.orderRepository = orderRepository;
    }

    public Review submitOrUpdateReview(Long orderId, Long orderItemId, Long productId, Long userId, int rating, String comment) {

        System.out.println("Received review request:");
        System.out.println("OrderId: " + orderId);
        System.out.println("OrderItemId: " + orderItemId);
        System.out.println("ProductId: " + productId);
        System.out.println("UserId: " + userId);
        System.out.println("Rating: " + rating);
        System.out.println("Comment: " + comment);

        // ✅ Fetch the order directly
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Check if order is delivered
        System.out.println("Order Status: " + order.getOrderStatus());
        if (order.getOrderStatus() != OrderStatus.DELIVERED) {
            throw new RuntimeException("Cannot review undelivered products");
        }

        // Fetch the order item
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new RuntimeException("Order item not found"));

        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        User user = order.getUser(); // Directly get user from order

        // Check if user owns this order
        if (!user.getId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        Optional<Review> existingReview = reviewRepository.findByUserAndProductAndOrderItem(user, product, orderItem);

        Review review;
        if (existingReview.isPresent()) {
            review = existingReview.get();
            review.setRating(rating);
            review.setComment(comment);
        } else {
            review = new Review();
            review.setOrderItem(orderItem);
            review.setProduct(product);
            review.setUser(user);
            review.setRating(rating);
            review.setComment(comment);
        }

        return reviewRepository.save(review);
    }




    public Optional<Review> getReviewForUserAndProduct(Long userId, Long productId, Long orderItemId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));


        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new RuntimeException("Order item not found"));

        return reviewRepository.findByUserAndProductAndOrderItem(user, product, orderItem);
    }



    // Get all reviews for a specific product
    public List<ReviewResponseDTO> getReviewsByProductId(Long productId) {
        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return reviewRepository.findByProduct(product).stream()
                .map(review -> new ReviewResponseDTO(
                        review.getId(),
                        review.getRating(),
                        review.getComment(),
                        review.getUser().getUsername()
                ))
                .toList();
    }
}