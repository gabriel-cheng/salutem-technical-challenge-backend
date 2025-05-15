package com.example.demo.domain.customer_order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.domain.customer.Customer;
import com.example.demo.domain.customer_order_items.CustomerOrderItems;

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
@EqualsAndHashCode(of="customer_order_id")
public class CustomerOrder {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String customer_order_id;

    private String code;

    private String description;

    private String observation;

    private LocalDateTime created_at;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "customerOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomerOrderItems> customerOrderItems = new ArrayList<>();

    public CustomerOrder(RequestCustomerOrder requestCustomerOrder) {
        this.code = requestCustomerOrder.code();
        this.description = requestCustomerOrder.description();
        this.observation = requestCustomerOrder.observation();
        this.created_at = requestCustomerOrder.created_at();
    }

}
