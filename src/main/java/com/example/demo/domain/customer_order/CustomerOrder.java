package com.example.demo.domain.customer_order;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.domain.customer.Customer;
import com.example.demo.domain.customer_order_item_drink.CustomerOrderItemDrink;
import com.example.demo.domain.customer_order_item_hamburger.CustomerOrderItemHamburger;
import com.example.demo.domain.customer_order_observations.CustomerOrderObservations;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="customer_order")
@Entity(name="customer_order")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="customer_order_id")
public class CustomerOrder {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String customer_order_id;

    private String code;

    private String description;

    private String created_at;

    @ManyToOne
    @JoinColumn(name="customer_id")
    @JsonIgnoreProperties({"customerOrders"})
    private Customer customer;

    @OneToMany(mappedBy = "customer_order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"customer_order"})
    private List<CustomerOrderItemHamburger> hamburgers = new ArrayList<>();

    @OneToMany(mappedBy = "customer_order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"customer_order"})
    private List<CustomerOrderItemDrink> drinks = new ArrayList<>();

    @OneToMany(mappedBy = "customer_order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"customer_order", "customer_order_observation_id"})
    private List<CustomerOrderObservations> observations = new ArrayList<>();

    public CustomerOrder(RequestCustomerOrder requestCustomerOrder) {
        this.code = requestCustomerOrder.code();
        this.description = requestCustomerOrder.description();
        this.created_at = requestCustomerOrder.created_at();
    }

}
