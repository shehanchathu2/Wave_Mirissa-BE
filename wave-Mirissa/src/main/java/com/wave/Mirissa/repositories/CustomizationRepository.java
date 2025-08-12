package com.wave.Mirissa.repositories;

import com.wave.Mirissa.models.Customization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomizationRepository extends JpaRepository<Customization,Long> {
    List<Customization> findByFaceShapeTagsIgnoreCaseAndSkinToneTagsIgnoreCase(String faceShapeTags, String skinToneTags);
}
