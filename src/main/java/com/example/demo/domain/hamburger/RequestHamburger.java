package com.example.demo.domain.hamburger;

public record RequestHamburger(
    String code,
    String description,
    double unity_price,
    String additional_flag
) { }
