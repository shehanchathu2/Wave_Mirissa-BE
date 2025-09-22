package com.wave.Mirissa.repositories;

import com.wave.Mirissa.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findByUserId(Long userId);
}