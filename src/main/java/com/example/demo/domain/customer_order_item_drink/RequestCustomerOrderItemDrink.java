package com.example.demo.domain.customer_order_item_drink;

import com.example.demo.domain.customer_order.CustomerOrder;
import com.example.demo.domain.drink.Drink;

public record RequestCustomerOrderItemDrink(
    CustomerOrder customer_order,
    Drink drink
) {

}
