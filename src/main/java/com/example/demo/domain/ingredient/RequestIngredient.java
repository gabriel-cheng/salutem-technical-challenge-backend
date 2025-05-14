package com.example.demo.domain.ingredient;

public record RequestIngredient(
    String code,
    String description,
    double unity_price,
    String additional_flag
) { }
