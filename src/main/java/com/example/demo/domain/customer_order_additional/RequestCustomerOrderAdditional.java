package com.example.demo.domain.customer_order_additional;

import com.example.demo.domain.customer_order.CustomerOrder;
import com.example.demo.domain.ingredient.Ingredient;

public record RequestCustomerOrderAdditional(
    Ingredient ingredient,
    CustomerOrder customerOrder
) {}
