package com.example.demo.domain.customer_order_items;

import java.util.List;

import com.example.demo.domain.customer_order.CustomerOrder;
import com.example.demo.domain.customer_order_item_drink.CustomerOrderItemDrink;
import com.example.demo.domain.customer_order_item_hamburger.CustomerOrderItemHamburger;

public record RequestCustomerOrderItems(
    CustomerOrder customer_order,
    List<CustomerOrderItemHamburger> hamburgers,
    List<CustomerOrderItemDrink> drinks
) { }
