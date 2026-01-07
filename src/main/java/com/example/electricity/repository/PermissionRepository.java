package com.example.electricity.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.electricity.entity.Permission;

public interface PermissionRepository extends JpaRepository <Permission,Long> {
}
