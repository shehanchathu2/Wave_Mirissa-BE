package com.wave.Mirissa.services;

import com.wave.Mirissa.models.Products;
import com.wave.Mirissa.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NecklaceService {

    private final ProductRepository productRepository;

    public NecklaceService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private String normalize(String s) {
        if (s == null) return "";
        return s.toLowerCase().replaceAll("\\s+", ""); // lowercase, remove spaces
    }

    /**
     * Return imageUrl1 for the first product whose 'personalize' normalized matches given personality normalized.
     * Returns null if not found.
     */
    public String getNecklaceImageUrlByPersonality(String personality) {
        if (personality == null || personality.isEmpty()) return null;
        String target = normalize(personality);

        List<Products> necklaces = productRepository.findByTypeForDbIgnoreCase("neckless"); // note: your DB type is 'neckless'
        for (Products p : necklaces) {
            String pPersonalize = p.getPersonalize();
            if (pPersonalize == null) continue;
            if (normalize(pPersonalize).equals(target)) {
                return p.getImageUrl1(); // Cloudinary URL saved in DB
            }
        }
        return null;
    }
}
