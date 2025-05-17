package com.example.demo.domain.customer_order_item_hamburger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.customer_order.CustomerOrder;

public interface CustomerOrderItemHamburgerRepository extends JpaRepository<CustomerOrderItemHamburger, String> {
    void deleteAllByCustomerOrder(CustomerOrder customerOrder);
}
