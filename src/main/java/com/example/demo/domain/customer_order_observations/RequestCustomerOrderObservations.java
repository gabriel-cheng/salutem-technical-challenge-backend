package com.example.demo.domain.customer_order_observations;

import com.example.demo.domain.customer_order.CustomerOrder;

public record RequestCustomerOrderObservations(
    CustomerOrder customer_order,
    String customer_order_observation
) { }
