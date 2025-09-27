package com.wave.Mirissa.repositories;

import com.wave.Mirissa.models.OrderItem;
import com.wave.Mirissa.models.Products;
import com.wave.Mirissa.models.Review;
import com.wave.Mirissa.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByUserAndProduct(User user, Products product);

    List<Review> findByProduct(Products product);
    Optional<Review> findByUserAndProductAndOrderItem(User user, Products product, OrderItem orderItem);



}