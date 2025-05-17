package com.example.demo.domain.customer_order_additional;

import com.example.demo.domain.customer_order.CustomerOrder;
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

@Table(name="customer_order_additional")
@Entity(name="customer_order_additional")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="customer_order_additional_id")
public class CustomerOrderAdditional {

    @Id @GeneratedValue(strategy=GenerationType.UUID)
    private String customer_order_additional_id;

    @ManyToOne
    @JoinColumn(name="customer_order_id")
    private CustomerOrder customerOrder;

    @ManyToOne
    @JoinColumn(name="ingredient")
    private Ingredient ingredient;

    public CustomerOrderAdditional(RequestCustomerOrderAdditional requestCustomerOrderAdditional) {
        this.ingredient = requestCustomerOrderAdditional.ingredient();
    }

}
