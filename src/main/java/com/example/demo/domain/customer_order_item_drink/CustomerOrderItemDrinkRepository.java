package com.example.demo.domain.customer_order_item_drink;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.customer_order.CustomerOrder;

public interface CustomerOrderItemDrinkRepository extends JpaRepository<CustomerOrderItemDrink, String> {
    void deleteAllByCustomerOrder(CustomerOrder customerOrder);
}
