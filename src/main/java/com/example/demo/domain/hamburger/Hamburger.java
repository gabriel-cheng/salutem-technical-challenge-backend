package com.example.demo.domain.hamburger;

import java.util.List;

import com.example.demo.domain.hamburger_ingredients.HamburgerIngredients;

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
@EqualsAndHashCode(of="hamburger_id")
public class Hamburger {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String hamburger_id;

    private String code;

    private String description;

    private double unity_price;

    @OneToMany(mappedBy="hamburger")
    private List<HamburgerIngredients> ingredients;

    public Hamburger(RequestHamburger requestHamburger) {
        this.code = requestHamburger.code();
        this.description = requestHamburger.description();
        this.unity_price = requestHamburger.unity_price();
    }

}
