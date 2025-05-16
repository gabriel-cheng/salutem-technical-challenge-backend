package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.responses.ResponseUtils;
import com.example.demo.domain.customer_order.CustomerOrder;
import com.example.demo.domain.customer_order.CustomerOrderRepository;
import com.example.demo.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping("/customer_order")
public class CustomerOrderController {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @GetMapping
    public ResponseEntity<List<CustomerOrder>> getAllCustomerOrders() {
        return ResponseUtils.getListResponse(
            customerOrderRepository::findAll,
            "Customer Order"
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerOrder> getOneCustomerOrder(
        @PathVariable String id
    ) throws ResourceNotFoundException {
        return ResponseUtils.getOneResponse(
            id,
            customerOrderRepository::findById,
            "Customer Order"
        );
    }
/*
    @PostMapping
    public ResponseEntity<String> registerNewCustomerOrder(
        @RequestBody @Validated RequestCustomerOrder customerOrder
    ) {
        try {
            CustomerOrder newCustomerOrder = new CustomerOrder(customerOrder);




        } catch(Exception error) {
            System.out.println("Failed to register that customer order: " + error.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An unexpected error ocurred. Please, try again later!");
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateCustomerOrder(
        @PathVariable String id,
        @RequestBody @Validated RequestCustomerOrder customerOrder
    ) {
        // logic
    }
    */

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomerOrder(
        @PathVariable String id
    ) throws ResourceNotFoundException {
        return ResponseUtils.deleteEntityResponse(
            () -> customerOrderRepository.findById(id),
            customerOrderRepository::delete,
            "Customer Order",
            id
        );
    }

}
