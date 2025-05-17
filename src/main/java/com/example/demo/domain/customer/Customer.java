package com.example.demo.domain.customer;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.domain.customer_order.CustomerOrder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="customer")
@Entity(name="customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="customer_id")
public class Customer {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String customer_id;

    private String name;

    private String address;

    private String cell;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"customerOrders", "customer"})
    private List<CustomerOrder> customerOrders = new ArrayList<>();

    public Customer(RequestCustomer requestCustomer) {
        this.name = requestCustomer.name();
        this.address = requestCustomer.address();
        this.cell = requestCustomer.cell();
    }

}
