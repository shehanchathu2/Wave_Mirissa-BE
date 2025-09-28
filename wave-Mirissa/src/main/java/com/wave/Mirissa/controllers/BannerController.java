package com.wave.Mirissa.controllers;

import com.wave.Mirissa.models.Banner;
import com.wave.Mirissa.services.BannerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/banners")
@CrossOrigin(origins = "*") // allow requests from frontend
public class BannerController {
    private final BannerService bannerService;

    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    // Get all banners
    @GetMapping
    public ResponseEntity<List<Banner>> getAllBanners() {
        return ResponseEntity.ok(bannerService.getAllBanners());
    }

    // Get the active banner — must be BEFORE get by ID
    @GetMapping("/active")
    public ResponseEntity<Banner> getActiveBanner() {
        return bannerService.getActiveBanner()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get banner by ID
    @GetMapping("/{id}")
    public ResponseEntity<Banner> getBanner(@PathVariable Long id) {
        return bannerService.getBannerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create new banner
    @PostMapping
    public ResponseEntity<Banner> createBanner(@RequestBody Banner banner) {
        Banner saved = bannerService.saveBanner(banner);
        return ResponseEntity.ok(saved);
    }

    // Update banner
    @PutMapping("/{id}")
    public ResponseEntity<Banner> updateBanner(@PathVariable Long id, @RequestBody Banner banner) {
        return bannerService.getBannerById(id).map(existing -> {
            existing.setTitle(banner.getTitle());
            existing.setImageUrl(banner.getImageUrl());
            Banner updated = bannerService.saveBanner(existing);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Delete banner
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable Long id) {
        bannerService.deleteBanner(id);
        return ResponseEntity.noContent().build();
    }

    // Activate a banner (only one active at a time)
    @PutMapping("/{id}/activate")
    public ResponseEntity<Banner> activateBanner(@PathVariable Long id) {
        Banner activated = bannerService.activateBanner(id);
        return ResponseEntity.ok(activated);
    }
}
