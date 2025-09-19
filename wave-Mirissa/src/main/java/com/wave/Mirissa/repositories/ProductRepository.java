package com.wave.Mirissa.repositories;

import com.wave.Mirissa.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {
    List<Products> findByFaceShapeTagsIgnoreCaseAndSkinToneTagsIgnoreCase(String faceShapeTags, String skinToneTags);

    // Fetch by type (neckless) so we can filter personalize in Java
    List<Products> findByTypeForDbIgnoreCase(String typeForDb);
}
