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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.customer.Customer;
import com.example.demo.domain.customer.CustomerRepository;
import com.example.demo.domain.customer_order.CustomerOrder;
import com.example.demo.domain.customer_order.CustomerOrderRepository;
import com.example.demo.domain.customer_order.RequestCustomerOrder;
import com.example.demo.domain.customer_order_additional.CustomerOrderAdditional;
import com.example.demo.domain.customer_order_additional.CustomerOrderAdditionalRepository;
import com.example.demo.domain.customer_order_item_drink.CustomerOrderItemDrink;
import com.example.demo.domain.customer_order_item_drink.CustomerOrderItemDrinkRepository;
import com.example.demo.domain.customer_order_item_hamburger.CustomerOrderItemHamburger;
import com.example.demo.domain.customer_order_item_hamburger.CustomerOrderItemHamburgerRepository;
import com.example.demo.domain.customer_order_observations.CustomerOrderObservations;
import com.example.demo.domain.customer_order_observations.CustomerOrderObservationsRepository;
import com.example.demo.domain.drink.Drink;
import com.example.demo.domain.drink.DrinkRepository;
import com.example.demo.domain.hamburger.Hamburger;
import com.example.demo.domain.hamburger.HamburgerRepository;
import com.example.demo.domain.ingredient.Ingredient;
import com.example.demo.domain.ingredient.IngredientRepository;
import com.example.demo.exceptions.InvalidAdditionalIngredientException;
import com.example.demo.exceptions.InvalidItemCodeException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.services.CustomerOrderService;
import com.example.demo.utils.ResponseUtils;

@RestController
@RequestMapping("/customer_order")
public class CustomerOrderController {

    private final CustomerOrderService customerOrderService;

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

    @Autowired
    private CustomerOrderObservationsRepository customerOrderObservationsRepository;

    @Autowired
    private CustomerOrderAdditionalRepository customerOrderAdditionalRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    public CustomerOrderController(CustomerOrderService customerOrderService) {
        this.customerOrderService = customerOrderService;
    }

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
    ) {
        try {
            boolean existsByCode = customerOrderRepository.existsByCode(customerOrder.code());
            if (existsByCode) {
                throw new InvalidItemCodeException("There is already an order with code " + customerOrder.code());
            }

            if (
                customerOrder.hamburger_id().isEmpty() &&
                customerOrder.drink_id().isEmpty()
            ) {
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("To generate an order, you need at least one item.");
            }

            Customer customerFound = customerRepository.findById(customerOrder.customer_id())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found!"));

            CustomerOrder newCustomerOrder = new CustomerOrder(customerOrder);
            newCustomerOrder.setCustomer(customerFound);

            double final_price = 0;

            List<CustomerOrderItemHamburger> hamburgers = new ArrayList<>();
            for (String hamburger_id : customerOrder.hamburger_id()) {
                Hamburger hamburgerFound = hamburgerRepository.findById(hamburger_id)
                    .orElseThrow(() -> new ResourceNotFoundException("Hamburger not found!"));

                CustomerOrderItemHamburger item = new CustomerOrderItemHamburger();
                item.setHamburger(hamburgerFound);
                item.setCustomerOrder(newCustomerOrder);
                hamburgers.add(item);

                final_price += hamburgerFound.getUnity_price();
            }
            newCustomerOrder.setHamburgers(hamburgers);

            List<CustomerOrderItemDrink> drinks = new ArrayList<>();
            for (String drink_id : customerOrder.drink_id()) {
                Drink drinkFound = drinkRepository.findById(drink_id)
                    .orElseThrow(() -> new ResourceNotFoundException("Drink not found!"));

                CustomerOrderItemDrink item = new CustomerOrderItemDrink();
                item.setDrink(drinkFound);
                item.setCustomerOrder(newCustomerOrder);
                drinks.add(item);

                final_price += drinkFound.getUnity_price();
            }
            newCustomerOrder.setDrinks(drinks);

            List<CustomerOrderObservations> observations = new ArrayList<>();
            for (String obs : customerOrder.observations()) {
                CustomerOrderObservations observation = new CustomerOrderObservations();
                observation.setCustomer_order_observation(obs);
                observation.setCustomerOrder(newCustomerOrder);
                observations.add(observation);
            }

            List<CustomerOrderAdditional> additional = new ArrayList<>();
            for (String ingredient_id : customerOrder.additional()) {
                Ingredient ingredient = ingredientRepository.findById(ingredient_id)
                    .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found!"));

                if (!"yes".equalsIgnoreCase(ingredient.getAdditional_flag())) {
                    throw new InvalidAdditionalIngredientException("Ingredient " + ingredient_id + " is not an additional item.");
                }

                CustomerOrderAdditional add = new CustomerOrderAdditional();
                add.setIngredient(ingredient);
                add.setCustomerOrder(newCustomerOrder);
                additional.add(add);

                final_price += ingredient.getUnity_price();
            }

            newCustomerOrder.setFinal_price(final_price);

            customerOrderRepository.save(newCustomerOrder);
            customerOrderItemHamburgerRepository.saveAll(hamburgers);
            customerOrderItemDrinkRepository.saveAll(drinks);
            customerOrderObservationsRepository.saveAll(observations);
            customerOrderAdditionalRepository.saveAll(additional);

            return ResponseEntity.status(HttpStatus.OK).body("Order created successfully!");

        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (InvalidItemCodeException | InvalidAdditionalIngredientException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCustomerOrder(
        @PathVariable String id,
        @RequestBody @Validated RequestCustomerOrder customerOrder
    ) throws InvalidItemCodeException {
        try {
            customerOrderService.updateCustomerOrder(id, customerOrder);
            return ResponseEntity.ok("Order updated successfully!");
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (InvalidAdditionalIngredientException | IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
        }
    }

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
