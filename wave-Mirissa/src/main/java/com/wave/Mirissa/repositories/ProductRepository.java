package com.wave.Mirissa.repositories;

import com.wave.Mirissa.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Products,Long> {

}
