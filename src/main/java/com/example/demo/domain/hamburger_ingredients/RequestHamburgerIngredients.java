package com.example.demo.domain.hamburger_ingredients;

import com.example.demo.domain.hamburger.Hamburger;
import com.example.demo.domain.ingredient.Ingredient;

public record RequestHamburgerIngredients(
    Hamburger hamburger,
    Ingredient ingredient,
    int quantity
) {}
