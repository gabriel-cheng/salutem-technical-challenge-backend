package com.example.demo.domain.customer_order_item_hamburger;

import com.example.demo.domain.customer_order.CustomerOrder;
import com.example.demo.domain.hamburger.Hamburger;

public record RequestCustomerOrderItemHamburger(
    CustomerOrder customer_order,
    Hamburger hamburger
) {

}
