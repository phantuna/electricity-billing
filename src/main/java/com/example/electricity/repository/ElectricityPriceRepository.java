package com.example.electricity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.electricity.entity.ElectricityPrice;

public interface ElectricityPriceRepository extends JpaRepository<ElectricityPrice,Long>{
    List<ElectricityPrice> findAlllByOrderByFromKwhAsc();
    boolean existsByFromKwhAndToKwh(Integer fromKwh, Integer toKwh);
}
