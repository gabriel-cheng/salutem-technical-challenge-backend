package com.example.demo.domain.customer_order;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, String> {

    boolean existsByCode(String code);

    Optional<CustomerOrder> findByCode(String code);

}
