package com.example.electricity.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "electricityPrice")
public class ElectricityPrice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private Integer fromKwh;
    private Integer toKwh;
    private Integer price;

}
