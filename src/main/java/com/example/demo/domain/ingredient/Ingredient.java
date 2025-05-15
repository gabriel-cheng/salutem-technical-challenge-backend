package com.example.demo.domain.ingredient;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="ingredient")
@Entity(name="ingredient")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="ingredient_id")
public class Ingredient {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String ingredient_id;

    private String code;

    private String description;

    private double unity_price;

    private String additional_flag;

    public Ingredient(RequestIngredient requestIngredient) {
        this.code = requestIngredient.code();
        this.description = requestIngredient.description();
        this.unity_price = requestIngredient.unity_price();
        this.additional_flag = requestIngredient.additional_flag();
    }

}
