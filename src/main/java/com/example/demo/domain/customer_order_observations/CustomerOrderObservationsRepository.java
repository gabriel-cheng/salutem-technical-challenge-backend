package com.example.demo.domain.customer_order_observations;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.customer_order.CustomerOrder;

public interface CustomerOrderObservationsRepository extends JpaRepository<CustomerOrderObservations, String> {
    void deleteAllByCustomerOrder(CustomerOrder customerOrder);
}
