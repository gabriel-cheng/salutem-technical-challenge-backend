package com.example.demo.domain.hamburger_ingredients;

import com.example.demo.domain.hamburger.Hamburger;
import com.example.demo.domain.ingredient.Ingredient;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="hamburger_ingredients")
@Entity(name="hamburger_ingredients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="hamburger_ingredients_id")
public class HamburgerIngredients {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String hamburger_ingredients_id;

    @ManyToOne
    @JoinColumn(name="hamburger_id")
    private Hamburger hamburger;

    @ManyToOne
    @JoinColumn(name="ingredient_id")
    private Ingredient ingredient;

    public HamburgerIngredients(
        RequestHamburgerIngredients requestHamburgerIngredients
    ) {
        this.hamburger = requestHamburgerIngredients.hamburger();
        this.ingredient = requestHamburgerIngredients.ingredient();
    }


}
