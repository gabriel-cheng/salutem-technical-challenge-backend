package com.example.demo.domain.hamburger;

import java.util.List;

import com.example.demo.domain.hamburger_ingredients.HamburgerIngredients;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="hamburger")
@Entity(name="hamburger")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="hamburgerId")
public class Hamburger {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="hamburger_id")
    private String hamburgerId;

    private String code;

    private String description;

    private double unity_price;

    @OneToMany(mappedBy="hamburger", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnoreProperties({"hamburger", "hamburger_ingredients_id"})
    private List<HamburgerIngredients> ingredients;

    public Hamburger(RequestHamburger requestHamburger) {
        this.code = requestHamburger.code();
        this.description = requestHamburger.description();
        this.unity_price = requestHamburger.unity_price();
    }

}
