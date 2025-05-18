package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

@Service
public class CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;
    private final CustomerRepository customerRepository;
    private final HamburgerRepository hamburgerRepository;
    private final DrinkRepository drinkRepository;
    private final CustomerOrderItemHamburgerRepository hamburgerItemRepository;
    private final CustomerOrderItemDrinkRepository drinkItemRepository;
    private final CustomerOrderObservationsRepository observationsRepository;
    private final IngredientRepository ingredientRepository;
    private final CustomerOrderAdditionalRepository customerOrderAdditionalRepository;

    public CustomerOrderService(
        CustomerOrderRepository customerOrderRepository,
        CustomerRepository customerRepository,
        HamburgerRepository hamburgerRepository,
        DrinkRepository drinkRepository,
        CustomerOrderItemHamburgerRepository hamburgerItemRepository,
        CustomerOrderItemDrinkRepository drinkItemRepository,
        CustomerOrderObservationsRepository observationsRepository,
        IngredientRepository ingredientRepository,
        CustomerOrderAdditionalRepository customerOrderAdditionalRepository
    ) {
        this.customerOrderRepository = customerOrderRepository;
        this.customerRepository = customerRepository;
        this.hamburgerRepository = hamburgerRepository;
        this.drinkRepository = drinkRepository;
        this.hamburgerItemRepository = hamburgerItemRepository;
        this.drinkItemRepository = drinkItemRepository;
        this.observationsRepository = observationsRepository;
        this.ingredientRepository = ingredientRepository;
        this.customerOrderAdditionalRepository = customerOrderAdditionalRepository;
    }

    @Transactional
    public void updateCustomerOrder(String id, RequestCustomerOrder request)
        throws ResourceNotFoundException, InvalidAdditionalIngredientException, InvalidItemCodeException {

        CustomerOrder existingOrder = customerOrderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order " + id + " not found."));

        Customer customer = customerRepository.findById(request.customer_id())
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found."));

        Optional<CustomerOrder> orderWithSameCode = customerOrderRepository.findByCode(request.code());
        if (orderWithSameCode.isPresent() && !orderWithSameCode.get().getCustomerOrderId().equals(id)) {
            throw new InvalidItemCodeException("There is already an order with code " + request.code());
        }

        existingOrder.setCustomer(customer);
        existingOrder.setCode(request.code());
        existingOrder.setFinal_price(request.final_price());
        existingOrder.setCreated_at(request.created_at());
        existingOrder.setDescription(request.description());

        hamburgerItemRepository.deleteAllByCustomerOrder(existingOrder);
        drinkItemRepository.deleteAllByCustomerOrder(existingOrder);
        observationsRepository.deleteAllByCustomerOrder(existingOrder);
        customerOrderAdditionalRepository.deleteAllByCustomerOrder(existingOrder);

        List<CustomerOrderItemHamburger> hamburgers = new ArrayList<>();
        for (String hamburger_id : request.hamburger_id()) {
            Hamburger hamburger = hamburgerRepository.findById(hamburger_id)
                .orElseThrow(() -> new ResourceNotFoundException("Hamburger not found: " + hamburger_id));
            CustomerOrderItemHamburger item = new CustomerOrderItemHamburger();
            item.setHamburger(hamburger);
            item.setCustomerOrder(existingOrder);
            hamburgers.add(item);
        }

        List<CustomerOrderItemDrink> drinks = new ArrayList<>();
        for (String drink_id : request.drink_id()) {
            Drink drink = drinkRepository.findById(drink_id)
                .orElseThrow(() -> new ResourceNotFoundException("Drink not found: " + drink_id));
            CustomerOrderItemDrink item = new CustomerOrderItemDrink();
            item.setDrink(drink);
            item.setCustomerOrder(existingOrder);
            drinks.add(item);
        }

        List<CustomerOrderAdditional> additional = new ArrayList<>();
        for (String ingredient_id : request.additional()) {
            Ingredient ingredient = ingredientRepository.findById(ingredient_id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found!"));

            if (!"yes".equalsIgnoreCase(ingredient.getAdditional_flag())) {
                throw new InvalidAdditionalIngredientException(
                    "Ingredient " + ingredient_id + " is not an additional item."
                );
            }

            CustomerOrderAdditional add = new CustomerOrderAdditional();
            add.setIngredient(ingredient);
            add.setCustomerOrder(existingOrder);
            additional.add(add);
        }

        List<CustomerOrderObservations> observations = request.observations().stream()
            .map(obs -> {
                CustomerOrderObservations o = new CustomerOrderObservations();
                o.setCustomerOrder(existingOrder);
                o.setCustomer_order_observation(obs);
                return o;
            }).collect(Collectors.toList());

        customerOrderRepository.save(existingOrder);
        hamburgerItemRepository.saveAll(hamburgers);
        drinkItemRepository.saveAll(drinks);
        observationsRepository.saveAll(observations);
        customerOrderAdditionalRepository.saveAll(additional);
    }

}
