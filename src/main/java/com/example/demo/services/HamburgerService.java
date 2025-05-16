package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.hamburger.Hamburger;
import com.example.demo.domain.hamburger.HamburgerRepository;
import com.example.demo.domain.hamburger.RequestHamburger;
import com.example.demo.domain.hamburger_ingredients.HamburgerIngredients;
import com.example.demo.domain.hamburger_ingredients.HamburgerIngredientsRepository;
import com.example.demo.domain.ingredient.Ingredient;
import com.example.demo.domain.ingredient.IngredientQuantity;
import com.example.demo.domain.ingredient.IngredientRepository;
import com.example.demo.exceptions.ResourceNotFoundException;

@Service
public class HamburgerService {

    private final HamburgerRepository hamburgerRepository;
    private final IngredientRepository ingredientRepository;
    private final HamburgerIngredientsRepository hamburgerIngredientsRepository;

    public HamburgerService(
        HamburgerRepository hamburgerRepository,
        IngredientRepository ingredientRepository,
        HamburgerIngredientsRepository hamburgerIngredientsRepository
    ) {
        this.hamburgerRepository = hamburgerRepository;
        this.ingredientRepository = ingredientRepository;
        this.hamburgerIngredientsRepository = hamburgerIngredientsRepository;
    }

    @Transactional
    public void updateHamburger(String id, RequestHamburger request)
            throws ResourceNotFoundException, IllegalArgumentException {

        Hamburger hamburger = hamburgerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Hamburger " + id + " not found."));

        if (request.ingredients() == null || request.ingredients().isEmpty()) {
            throw new IllegalArgumentException("Ingredients can't be empty.");
        }

        List<Ingredient> validatedIngredients = new ArrayList<>();
        for (IngredientQuantity iq : request.ingredients()) {
            Ingredient ingredient = ingredientRepository.findById(iq.ingredient_id())
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient " + iq.ingredient_id() + " not found."));
            validatedIngredients.add(ingredient);
        }

        hamburger.setCode(request.code());
        hamburger.setDescription(request.description());
        hamburger.setUnity_price(request.unity_price());

        hamburgerIngredientsRepository.deleteByHamburger(hamburger);

        List<HamburgerIngredients> newRelations = validatedIngredients.stream()
            .map(ingredient -> new HamburgerIngredients(hamburger, ingredient))
            .collect(Collectors.toList());

        hamburgerRepository.save(hamburger);
        hamburgerIngredientsRepository.saveAll(newRelations);
    }

}
