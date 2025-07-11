package com.wave.Mirissa.repositories;

import com.wave.Mirissa.models.Cart;
import com.wave.Mirissa.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
