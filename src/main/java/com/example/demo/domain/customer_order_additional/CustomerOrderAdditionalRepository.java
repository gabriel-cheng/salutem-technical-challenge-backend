package com.example.demo.domain.customer_order_additional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.customer_order.CustomerOrder;

public interface CustomerOrderAdditionalRepository extends JpaRepository<CustomerOrderAdditional, String> {

    void deleteAllByCustomerOrder(CustomerOrder customerOrder);

}
