package com.example.demo.domain.customer_order_observations;

import com.example.demo.domain.customer_order.CustomerOrder;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="customer_order_observations")
@Entity(name="customer_order_observations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="customer_order_observation_id")
public class CustomerOrderObservations {

    @Id @GeneratedValue(strategy=GenerationType.UUID)
    private String customer_order_observation_id;

    private String customer_order_observation;

    @ManyToOne
    @JoinColumn(name="customer_order_id")
    private CustomerOrder customer_order;

    public CustomerOrderObservations(RequestCustomerOrderObservations requestCustomerOrderObservations) {
        this.customer_order_observation = requestCustomerOrderObservations.customer_order_observation();
        this.customer_order = requestCustomerOrderObservations.customer_order();
    }

}
