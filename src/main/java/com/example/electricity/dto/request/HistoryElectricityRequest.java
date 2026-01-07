package com.example.electricity.dto.request;

import java.time.LocalDate;

import org.springframework.lang.NonNull;

import lombok.Data;

@Data
public class HistoryElectricityRequest {
    @NonNull
    private Long userId;
    private Integer currentKwh;
    private LocalDate date;
}