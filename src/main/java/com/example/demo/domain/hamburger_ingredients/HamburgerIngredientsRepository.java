package com.example.demo.domain.hamburger_ingredients;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.hamburger.Hamburger;

public interface HamburgerIngredientsRepository extends JpaRepository<HamburgerIngredients, String> {
    void deleteByHamburger(Hamburger hamburger);
}
