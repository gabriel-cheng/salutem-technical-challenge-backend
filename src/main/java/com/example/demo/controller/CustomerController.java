package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.responses.ResponseUtils;
import com.example.demo.domain.customer.Customer;
import com.example.demo.domain.customer.CustomerRepository;
import com.example.demo.domain.customer.RequestCustomer;
import com.example.demo.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseUtils.getListResponse(
            customerRepository::findAll,
            "Customer"
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getOneCustomer(@PathVariable String id) throws ResourceNotFoundException {
        return ResponseUtils.getOneResponse(
            id,
            customerRepository::findById,
            "Customer"
        );
    }

    @PostMapping
    public ResponseEntity<String> registerNewCustomer(
        @RequestBody @Validated RequestCustomer customer
    ) {
        return ResponseUtils.registerNewEntity(
            customer,
            req -> new Customer(req),
            customerRepository::save,
            "Customer"
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(
        @RequestBody @Validated RequestCustomer customer,
        @PathVariable String id
    ) throws ResourceNotFoundException {
        return ResponseUtils.updateEntity(
            customer,
            () -> customerRepository.findById(id),
            (request, entity) -> {
                entity.setName(request.name());
                entity.setAddress(request.address());
                entity.setCell(request.cell());
            },
            customerRepository::save,
            "Customer",
            id
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String id) throws ResourceNotFoundException {
        return ResponseUtils.deleteEntityResponse(
            () -> customerRepository.findById(id),
            customerRepository::delete,
            "Customer",
            id
        );
    }

}
