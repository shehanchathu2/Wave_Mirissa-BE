package com.wave.Mirissa.repositories;

import com.wave.Mirissa.models.Role;
import com.wave.Mirissa.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);

    long countByRole(Role role);
}