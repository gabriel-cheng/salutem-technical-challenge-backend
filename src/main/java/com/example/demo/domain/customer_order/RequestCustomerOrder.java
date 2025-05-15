package com.example.demo.domain.customer_order;

import java.time.LocalDateTime;

public record RequestCustomerOrder(
    String code,
    String description,
    String observation,
    String customer_id,
    LocalDateTime created_at
) {}
