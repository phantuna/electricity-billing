package com.example.electricity.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.electricity.entity.HistoryElectricity;

public interface HistoryElectricityRepository extends JpaRepository<HistoryElectricity, Long> {

    HistoryElectricity findTopByUserIdOrderByCreatedDateDesc(Long userId);

    List<HistoryElectricity> findByUserId(Long userId);

    @Query("SELECT h FROM HistoryElectricity h WHERE h.user.id = :userId AND MONTH(h.createdDate) = :month AND YEAR(h.createdDate) = :year")
    List<HistoryElectricity> findByUserIdAndMonth(Long userId, int month, int year);

}
