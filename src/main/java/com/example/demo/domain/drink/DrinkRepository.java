package com.example.demo.domain.drink;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkRepository extends JpaRepository<Drink, String> {

    boolean existsByCode(String code);

    boolean existsByCodeAndDrinkIdNot(String code, String drinkId);

}
