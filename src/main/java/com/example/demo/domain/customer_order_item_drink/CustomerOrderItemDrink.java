package com.example.demo.domain.customer_order_item_drink;

import com.example.demo.domain.customer_order.CustomerOrder;
import com.example.demo.domain.drink.Drink;

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

@Table(name="customer_order_item_drink")
@Entity(name="customer_order_item_drink")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="customer_order_item_drink_id")
public class CustomerOrderItemDrink {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String customer_order_item_drink_id;

        @ManyToOne
    @JoinColumn(name="customer_order_id")
    private CustomerOrder customer_order;

    @ManyToOne
    @JoinColumn(name="drink_id")
    private Drink drink;

    public CustomerOrderItemDrink(RequestCustomerOrderItemDrink requestCustomerOrderItemDrink) {
        this.customer_order = requestCustomerOrderItemDrink.customer_order();
        this.drink = requestCustomerOrderItemDrink.drink();
    }

}
