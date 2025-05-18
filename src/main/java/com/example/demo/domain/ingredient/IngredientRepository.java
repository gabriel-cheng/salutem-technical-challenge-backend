package com.example.demo.domain.ingredient;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, String> {

    boolean existsByCode(String code);

    boolean existsByCodeAndIngredientIdNot(String code, String ingredientId);

}