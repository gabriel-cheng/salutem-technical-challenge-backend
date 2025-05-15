package com.example.demo.domain.hamburger;

import java.util.List;

import com.example.demo.domain.ingredient.IngredientQuantity;

public record RequestHamburger(
    String code,
    String description,
    double unity_price,
    List<IngredientQuantity> ingredients
) { }
