package com.example.electricity.dto.response;

import lombok.Data;

@Data
public class ElectricityPriceResponse {
    private Integer fromKwh;
    private Integer toKwh;
    private Integer price;
}
