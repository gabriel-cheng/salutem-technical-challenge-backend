package com.example.demo.domain.customer_order;

public record RequestCustomerOrder(
    String code,
    String description,
    String observation,
    String customer_id
) {}
