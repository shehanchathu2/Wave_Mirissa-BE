package com.wave.Mirissa.repositories;

import com.wave.Mirissa.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Products,Long> {
    List<Products> findByFaceShapeTagsIgnoreCaseAndSkinToneTagsIgnoreCase(String faceShapeTags, String skinToneTags);

    @Query("SELECT p.typeForDb, COUNT(p) FROM Products p GROUP BY p.typeForDb")
    List<Object[]> countProductsByCategory();

    @Query("SELECT p.name, COUNT(p) FROM Products p GROUP BY p.name")
    List<Object[]> countProductsByName();

    // Fetch by type (neckless) so we can filter personalize in Java
    List<Products> findByTypeForDbIgnoreCase(String typeForDb);


    List<Products> findByPersonalize(String none);

    // 🔑 new method for UUID lookups
    Optional<Products> findByUuid(UUID uuid);
}