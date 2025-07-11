package com.wave.Mirissa.repositories;

import com.wave.Mirissa.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
