package com.wave.Mirissa.services;

import com.wave.Mirissa.models.Banner;
import com.wave.Mirissa.repositories.BannerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BannerService {
    private final BannerRepository bannerRepository;

    public BannerService(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    // Get all banners
    public List<Banner> getAllBanners() {
        return bannerRepository.findAll();
    }

    // Get banner by ID
    public Optional<Banner> getBannerById(Long id) {
        return bannerRepository.findById(id);
    }

    // Save or update banner
    public Banner saveBanner(Banner banner) {
        return bannerRepository.save(banner);
    }

    // Delete banner
    public void deleteBanner(Long id) {
        bannerRepository.deleteById(id);
    }

    // Activate a banner (only one can be active at a time)
    @Transactional
    public Banner activateBanner(Long id) {
        // Deactivate all banners
        bannerRepository.updateAllActiveFalse();

        // Activate the selected banner
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banner not found"));
        banner.setActive(true);
        return bannerRepository.save(banner);
    }

    public Optional<Banner> getActiveBanner() {
        return bannerRepository.findActiveBanner();
    }

}
