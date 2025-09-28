package com.wave.Mirissa.controllers;

import com.wave.Mirissa.dtos.ReviewRequestDTO;
import com.wave.Mirissa.dtos.ReviewResponseDTO;
import com.wave.Mirissa.models.Review;
import com.wave.Mirissa.services.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:5173")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // ✅ Updated to accept images
    @PostMapping("/submit")
    public Review submitOrUpdateReview(@RequestBody ReviewRequestDTO request) {

        // Print the incoming request to check all fields
        System.out.println("===== Review Request Received =====");
        System.out.println("Order ID: " + request.getOrderId());
        System.out.println("Order Item ID: " + request.getOrderItemId());
        System.out.println("Product ID: " + request.getProductId());
        System.out.println("User ID: " + request.getUserId());
        System.out.println("Rating: " + request.getRating());
        System.out.println("Comment: " + request.getComment());
        System.out.println("Image URL 1: " + request.getImageUrl1());
        System.out.println("Image URL 2: " + request.getImageUrl2());
        System.out.println("Image URL 3: " + request.getImageUrl3());
        System.out.println("Image URL 4: " + request.getImageUrl4());
        System.out.println("Image URL 5: " + request.getImageUrl5());
        System.out.println("===================================");
        return reviewService.submitOrUpdateReview(
                request.getOrderId(),
                request.getOrderItemId(),
                request.getProductId(),
                request.getUserId(),
                request.getRating(),
                request.getComment(),
                request.getImageUrl1(),
                request.getImageUrl2(),
                request.getImageUrl3(),
                request.getImageUrl4(),
                request.getImageUrl5()
        );
    }

    @GetMapping("/user/{userId}/product/{productId}/order/{orderItemId}")
    public ResponseEntity<Review> getUserProductReview(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @PathVariable Long orderItemId) {
        return reviewService.getReviewForUserAndProduct(userId, productId, orderItemId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get reviews for a product
    @GetMapping("/product/{productId}")
    public List<ReviewResponseDTO> getProductReviews(@PathVariable Long productId) {
        return reviewService.getReviewsByProductId(productId);
    }
}
