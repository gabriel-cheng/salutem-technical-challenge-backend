package com.example.demo.domain.customer_order;

import java.time.LocalDateTime;
import java.util.List;

public record RequestCustomerOrder(
    String code,
    String description,
    List<String> observation,
    String customer_id,
    LocalDateTime created_at,
    List<String> hamburger_id,
    List<String> drink_id,
    List<String> customer_observations
) {}

