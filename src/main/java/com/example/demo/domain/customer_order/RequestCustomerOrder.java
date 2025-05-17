package com.example.demo.domain.customer_order;

import java.util.List;

public record RequestCustomerOrder(
    String code,
    String description,
    String customer_id,
    String created_at,
    List<String> observations,
    List<String> hamburger_id,
    List<String> drink_id
) {}