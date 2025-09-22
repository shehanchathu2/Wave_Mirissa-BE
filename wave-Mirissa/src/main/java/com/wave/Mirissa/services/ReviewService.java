package com.wave.Mirissa.services;

import com.wave.Mirissa.dtos.ReviewResponseDTO;
import com.wave.Mirissa.models.*;
import com.wave.Mirissa.repositories.OrderItemRepository;
import com.wave.Mirissa.repositories.ProductRepository;
import com.wave.Mirissa.repositories.ReviewRepository;
import com.wave.Mirissa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productsRepository;
    private final UserRepository userRepository; // assuming you have this



    public ReviewService(ReviewRepository reviewRepository, OrderItemRepository orderItemRepository,
                         ProductRepository productsRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.orderItemRepository = orderItemRepository;
        this.productsRepository = productsRepository;
        this.userRepository = userRepository;

    }

    public Review submitOrUpdateReview(Long orderItemId, Long productId, Long userId, int rating, String comment) {

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new RuntimeException("Order item not found"));

        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        User user = orderItem.getOrder().getUser();

        // Check if order is delivered
        if (orderItem.getOrder().getOrderStatus() != OrderStatus.DELIVERED) {
            throw new RuntimeException("Cannot review undelivered products");
        }

        // Check if user owns this order
        if (!user.getId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        // Check if review already exists for this user & product
        Optional<Review> existingReview = reviewRepository.findByUserAndProduct(user, product);

        Review review;
        if (existingReview.isPresent()) {
            // Update existing review
            review = existingReview.get();
            review.setRating(rating);
            review.setComment(comment);
        } else {
            // Create new review
            review = new Review();
            review.setOrderItem(orderItem);
            review.setProduct(product);
            review.setUser(user);
            review.setRating(rating);
            review.setComment(comment);
        }

        return reviewRepository.save(review);
    }

    public Optional<Review> getReviewForUserAndProduct(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return reviewRepository.findByUserAndProduct(user, product);
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