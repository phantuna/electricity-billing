package com.example.electricity.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryElectricityResponse {

    private String username;
    private Integer previousKwh;
    private Integer currentKwh;
    private Integer consumption;
    private Long totalAmount;
    private LocalDate Date;
}
