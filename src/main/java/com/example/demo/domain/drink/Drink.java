package com.example.demo.domain.drink;

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

@Table(name="drink")
@Entity(name="drink")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="drinkId")
public class Drink {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="drink_id")
    private String drinkId;

    private String code;

    private String description;

    private double unity_price;

    private String sugar_flag;

    public Drink(RequestDrink requestDrink) {
        this.code = requestDrink.code();
        this.description = requestDrink.description();
        this.unity_price = requestDrink.unity_price();
        this.sugar_flag = requestDrink.sugar_flag();
    }

}
