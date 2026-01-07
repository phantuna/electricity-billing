package com.example.electricity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import com.example.electricity.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{
    @NonNull
    Optional<User>findById(@NonNull Long id);
    Optional<User>findByUsername(String username);

    boolean existsByUsername(String username);
}
