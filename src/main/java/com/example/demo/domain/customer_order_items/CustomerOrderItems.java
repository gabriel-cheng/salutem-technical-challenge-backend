package com.example.demo.domain.customer_order_items;

import com.example.demo.domain.customer_order.CustomerOrder;
import com.example.demo.domain.drink.Drink;
import com.example.demo.domain.hamburger.Hamburger;

import jakarta.persistence.Column;
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

@Table(name="customer_order_items")
@Entity(name="customer_order_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="customer_order_items_id")
public class CustomerOrderItems {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="customer_order_items_id")
    private String customer_order_items_id;

    @ManyToOne
    @JoinColumn(name="customer_order_id")
    private CustomerOrder customerOrder;

    @ManyToOne
    @JoinColumn(name="hamburger_id")
    private Hamburger hamburger;

    @ManyToOne
    @JoinColumn(name="drink_id")
    private Drink drink;

    public CustomerOrderItems(RequestCustomerOrderItems requestCustomerOrderItems) {
        this.customerOrder = requestCustomerOrderItems.customer_order();
        this.hamburger = requestCustomerOrderItems.hamburger();
        this.drink = requestCustomerOrderItems.drink();
    }

}
