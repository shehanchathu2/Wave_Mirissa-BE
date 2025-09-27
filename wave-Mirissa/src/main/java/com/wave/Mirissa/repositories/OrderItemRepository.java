package com.wave.Mirissa.repositories;

import com.wave.Mirissa.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
    public interface OrderItemRepository extends JpaRepository<OrderItem ,Long>{
    @Query("SELECT oi FROM OrderItem oi JOIN FETCH oi.order WHERE oi.id = :id")
    Optional<OrderItem> findByIdWithOrder(@Param("id") Long id);


    }

