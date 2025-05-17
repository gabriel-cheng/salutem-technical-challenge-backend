package com.example.demo.domain.customer_order_item_hamburger;

import com.example.demo.domain.customer_order.CustomerOrder;
import com.example.demo.domain.hamburger.Hamburger;

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

@Table(name="customer_order_item_hamburger")
@Entity(name="customer_order_item_hamburger")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="customer_order_item_hamburger_id")
public class CustomerOrderItemHamburger {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String customer_order_item_hamburger_id;

        @ManyToOne
    @JoinColumn(name="customer_order_id")
    private CustomerOrder customerOrder;

    @ManyToOne
    @JoinColumn(name="drink_id")
    private Hamburger hamburger;

    public CustomerOrderItemHamburger(RequestCustomerOrderItemHamburger requestCustomerOrderItemDrink) {
        this.customerOrder = requestCustomerOrderItemDrink.customer_order();
        this.hamburger = requestCustomerOrderItemDrink.hamburger();
    }

}
