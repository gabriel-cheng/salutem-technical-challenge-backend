package com.example.demo.domain.drink;

public record RequestDrink(
    String code,
    String description,
    double unity_price,
    String sugar_flag
) { }
