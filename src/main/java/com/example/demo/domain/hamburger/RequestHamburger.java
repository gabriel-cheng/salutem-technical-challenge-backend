package com.example.demo.domain.hamburger;

import java.util.List;

public record RequestHamburger(
    String code,
    String description,
    double unity_price,
    List<String> ingredients_id
) { }
