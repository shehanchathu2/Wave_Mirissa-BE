package com.wave.Mirissa.services;

import com.wave.Mirissa.models.Products;
import com.wave.Mirissa.models.Wishlist;
import com.wave.Mirissa.repositories.ProductRepository;
import com.wave.Mirissa.repositories.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductRepository productRepository;

    public Wishlist addToWishlist(Long userId, Long productId) {
        if (!wishlistRepository.existsByUserIdAndProductId(userId, productId)) {
            Wishlist wishlist = new Wishlist();
            wishlist.setUserId(userId);
            wishlist.setProductId(productId);
            wishlist.setAddedAt(LocalDateTime.now().toString());
            return wishlistRepository.save(wishlist);
        }
        return null; // Already exists
    }

    @Transactional
    public void removeFromWishlist(Long userId, Long productId) {
        wishlistRepository.deleteByUserIdAndProductId(userId, productId);
    }

    public List<Wishlist> getWishlistByUser(Long userId) {
        return wishlistRepository.findByUserId(userId);
    }

    public List<Products> getWishlistProducts(Long userId) {
        List<Wishlist> wishlist = wishlistRepository.findByUserId(userId);
        List<Long> productIds = wishlist.stream()
                .map(Wishlist::getProductId)
                .collect(Collectors.toList());
        return productRepository.findAllById(productIds);
    }
}
