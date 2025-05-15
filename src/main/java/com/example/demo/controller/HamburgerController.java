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

import com.example.demo.controller.responses.ResponseUtils;
import com.example.demo.domain.hamburger.Hamburger;
import com.example.demo.domain.hamburger.HamburgerRepository;
import com.example.demo.domain.hamburger.RequestHamburger;
import com.example.demo.domain.hamburger_ingredients.HamburgerIngredients;
import com.example.demo.domain.hamburger_ingredients.HamburgerIngredientsRepository;
import com.example.demo.domain.ingredient.Ingredient;
import com.example.demo.domain.ingredient.IngredientQuantity;
import com.example.demo.domain.ingredient.IngredientRepository;
import com.example.demo.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping("/hamburger")
public class HamburgerController {

    @Autowired
    private HamburgerRepository hamburgerRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private HamburgerIngredientsRepository hamburgerIngredientsRepository;

    @GetMapping
    public ResponseEntity<List<Hamburger>> getAllHamburgers() {
        return ResponseUtils.getListResponse(
            hamburgerRepository::findAll,
            "Hamburger"
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hamburger> getOneHamburger(
        @PathVariable String id
    ) throws ResourceNotFoundException {
        return ResponseUtils.getOneResponse(
            id,
            hamburgerRepository::findById,
            "Hamburger"
        );
    }

    @PostMapping
    public ResponseEntity<String> registerNewHamburger(
        @RequestBody @Validated RequestHamburger hamburger
    ) {
        try {
            Hamburger newHamburger = new Hamburger(hamburger);
            hamburgerRepository.save(newHamburger);

            List<HamburgerIngredients> ingredientRelations = new ArrayList<>();

            for (IngredientQuantity iq : hamburger.ingredients()) {
                Ingredient ingredient = ingredientRepository.findById(iq.ingredient_id())
                    .orElseThrow(() -> new IllegalArgumentException("Ingredient " + iq.ingredient_id() + " not found."));

                HamburgerIngredients relation = new HamburgerIngredients();
                relation.setHamburger(newHamburger);
                relation.setIngredient(ingredient);
                relation.setQuantity(iq.quantity());

                ingredientRelations.add(relation);
            }

            hamburgerIngredientsRepository.saveAll(ingredientRelations);

            return ResponseEntity
            .status(HttpStatus.OK)
            .body("Hamburger created successfully!");
        } catch(Exception error) {
            System.out.println("Failed to register that category: " + error.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An unexpected error ocurred. Please, try again later!");
        }
    }

    @PutMapping
    public ResponseEntity<Hamburger> updateHamburger(
        @RequestBody @Validated RequestHamburger hamburger,
        @PathVariable String id
    ) throws ResourceNotFoundException {
        return ResponseUtils.updateEntity(
            hamburger,
            () -> hamburgerRepository.findById(id),
            (request, entity) -> {
                entity.setCode(request.code());
                entity.setDescription(request.description());
                entity.setUnity_price(request.unity_price());
            },
            hamburgerRepository::save,
            "Hamburger",
            id
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHamburger(@PathVariable String id) throws ResourceNotFoundException {
        return ResponseUtils.deleteEntityResponse(
            () -> hamburgerRepository.findById(id),
            hamburgerRepository::delete,
            "Hamburger",
            id
        );
    }

}
