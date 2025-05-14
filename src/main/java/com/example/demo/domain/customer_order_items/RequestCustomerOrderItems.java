package com.example.demo.domain.customer_order_items;

import com.example.demo.domain.customer_order.CustomerOrder;
import com.example.demo.domain.drink.Drink;
import com.example.demo.domain.hamburger.Hamburger;

public record RequestCustomerOrderItems(
    CustomerOrder customer_order,
    Hamburger hamburger,
    Drink drink
) { }
