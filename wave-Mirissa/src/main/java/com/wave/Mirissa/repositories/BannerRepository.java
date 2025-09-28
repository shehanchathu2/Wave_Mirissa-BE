package com.wave.Mirissa.repositories;

import com.wave.Mirissa.models.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Banner b SET b.active = false")
    void updateAllActiveFalse();

    @Query("SELECT b FROM Banner b WHERE b.active = true")
    Optional<Banner> findActiveBanner();

}
