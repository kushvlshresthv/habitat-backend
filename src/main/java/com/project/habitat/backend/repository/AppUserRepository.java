package com.project.habitat.backend.repository;

import com.project.habitat.backend.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
    public Optional<AppUser> findByUsername(String username);
    public boolean existsByUsername(String username);
}
