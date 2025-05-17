package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.responses.ResponseUtils;
import com.example.demo.domain.customer.Customer;
import com.example.demo.domain.customer.CustomerRepository;
import com.example.demo.domain.customer_order.CustomerOrder;
import com.example.demo.domain.customer_order.CustomerOrderRepository;
import com.example.demo.domain.customer_order.RequestCustomerOrder;
import com.example.demo.domain.customer_order_item_drink.CustomerOrderItemDrink;
import com.example.demo.domain.customer_order_item_drink.CustomerOrderItemDrinkRepository;
import com.example.demo.domain.customer_order_item_hamburger.CustomerOrderItemHamburger;
import com.example.demo.domain.customer_order_item_hamburger.CustomerOrderItemHamburgerRepository;
import com.example.demo.domain.drink.Drink;
import com.example.demo.domain.drink.DrinkRepository;
import com.example.demo.domain.hamburger.Hamburger;
import com.example.demo.domain.hamburger.HamburgerRepository;
import com.example.demo.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping("/customer_order")
public class CustomerOrderController {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private HamburgerRepository hamburgerRepository;

    @Autowired
    private DrinkRepository drinkRepository;

    @Autowired
    private CustomerOrderItemDrinkRepository customerOrderItemDrinkRepository;

    @Autowired
    private CustomerOrderItemHamburgerRepository customerOrderItemHamburgerRepository;

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

    @PostMapping
    public ResponseEntity<String> registerNewCustomerOrder(
        @RequestBody @Validated RequestCustomerOrder customerOrder
    ) throws ResourceNotFoundException {
        try {
            CustomerOrder newCustomerOrder = new CustomerOrder(customerOrder);
            Customer customerFound = customerRepository.findById(customerOrder.customer_id())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found!"));

            newCustomerOrder.setCustomer(customerFound);
            
            List<CustomerOrderItemHamburger> hamburgers = new ArrayList<>();
            List<CustomerOrderItemDrink> drinks = new ArrayList<>();

            if(
                customerOrder.hamburger_id().isEmpty() &&
                 customerOrder.drink_id().isEmpty()
            ) {
                return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("To generate an order, you need at least one item");
            }

            for (String hamburger_id : customerOrder.hamburger_id()) {
                Hamburger hamburgerFound = hamburgerRepository.findById(hamburger_id)
                    .orElseThrow(() -> new ResourceNotFoundException("Hamburger not found!"));
                
                Hamburger hamburger = hamburgerFound;

                CustomerOrderItemHamburger customerOrderItemHamburger = new CustomerOrderItemHamburger();
                customerOrderItemHamburger.setHamburger(hamburger);
                
                hamburgers.add(customerOrderItemHamburger);
                
                newCustomerOrder.setHamburgers(hamburgers);
            }
            
            for (String drink_id : customerOrder.drink_id()) {
                Drink drinkFound = drinkRepository.findById(drink_id)
                    .orElseThrow(() -> new ResourceNotFoundException("Drink not found!"));
                
                Drink drink = drinkFound;

                CustomerOrderItemDrink customerOrderItemDrink = new CustomerOrderItemDrink();
                customerOrderItemDrink.setDrink(drink);

                drinks.add(customerOrderItemDrink);

                newCustomerOrder.setDrinks(drinks);
            }
            
            customerOrderRepository.save(newCustomerOrder);

            customerOrderItemHamburgerRepository.saveAll(hamburgers);
            customerOrderItemDrinkRepository.saveAll(drinks);

            // ObjectMapper mapper = new ObjectMapper();

            // String json = mapper.writeValueAsString(newCustomerOrder);
            // System.out.println(json);

            return ResponseEntity
            .status(HttpStatus.OK)
            .body("Order created successfully!");
        } catch(Exception error) {
            System.out.println("Failed to register that customer order: " + error.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An unexpected error ocurred. Please, try again later!");
        }
    }

    /*
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
