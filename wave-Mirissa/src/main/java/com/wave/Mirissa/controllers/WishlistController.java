package com.wave.Mirissa.controllers;

import com.wave.Mirissa.models.Products;
import com.wave.Mirissa.models.Wishlist;
import com.wave.Mirissa.services.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
@CrossOrigin(origins = "http://localhost:3000") // Update to match frontend
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping("/add")
    public Wishlist addToWishlist(@RequestBody Wishlist request) {
        return wishlistService.addToWishlist(request.getUserId(), request.getProductId());
    }

    @DeleteMapping("/remove")
    public void removeFromWishlist(@RequestParam Long userId, @RequestParam Long productId) {
        wishlistService.removeFromWishlist(userId, productId);
    }

    @GetMapping("/{userId}")
    public List<Wishlist> getUserWishlist(@PathVariable Long userId) {
        return wishlistService.getWishlistByUser(userId);
    }

    @GetMapping("/{userId}/products")
    public List<Products> getWishlistProducts(@PathVariable Long userId) {
        return wishlistService.getWishlistProducts(userId);
    }
}
