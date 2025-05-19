package com.example.demo.domain.customer_order;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.domain.customer.Customer;
import com.example.demo.domain.customer_order_additional.CustomerOrderAdditional;
import com.example.demo.domain.customer_order_item_drink.CustomerOrderItemDrink;
import com.example.demo.domain.customer_order_item_hamburger.CustomerOrderItemHamburger;
import com.example.demo.domain.customer_order_observations.CustomerOrderObservations;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
@EqualsAndHashCode(of="customerOrderId")
public class CustomerOrder {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="customer_order_id")
    private String customerOrderId;

    private String code;

    private String description;

    private String created_at;

    private double final_price;

    @ManyToOne
    @JoinColumn(name="customer_id")
    @JsonIgnoreProperties({"customerOrders"})
    private Customer customer;

    @OneToMany(mappedBy = "customerOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"customerOrder", "customer_order_item_hamburger_id"})
    private List<CustomerOrderItemHamburger> hamburgers = new ArrayList<>();

    @OneToMany(mappedBy = "customerOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"customerOrder", "customer_order_item_drink_id"})
    private List<CustomerOrderItemDrink> drinks = new ArrayList<>();

    @OneToMany(mappedBy = "customerOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"customerOrder", "customer_order_observation_id"})
    private List<CustomerOrderObservations> observations = new ArrayList<>();

    @OneToMany(mappedBy = "customerOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"customerOrder", "customer_order_additional_id"})
    private List<CustomerOrderAdditional> additional = new ArrayList<>();

    public CustomerOrder(RequestCustomerOrder requestCustomerOrder) {
        this.code = requestCustomerOrder.code();
        this.description = requestCustomerOrder.description();
        this.created_at = requestCustomerOrder.created_at();
    }

}
