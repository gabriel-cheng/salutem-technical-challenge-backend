package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.example.demo.services.HamburgerService;

@RestController
@RequestMapping("/hamburger")
public class HamburgerController {

    @Autowired
    private HamburgerRepository hamburgerRepository;

    @Autowired
    private HamburgerService hamburgerService;

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
            List<HamburgerIngredients> ingredientRelations = new ArrayList<>();

            if(hamburger.ingredients().isEmpty()) {
                return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Ingredients can't be empty!");
            }

            for (IngredientQuantity iq : hamburger.ingredients()) {
                Optional<Ingredient> ingredientOpt = ingredientRepository.findById(iq.ingredient_id());
                if (ingredientOpt.isEmpty()) {
                    return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Ingredient " + iq.ingredient_id() + " not found.");
                }
                Ingredient ingredient = ingredientOpt.get();
                
                HamburgerIngredients relation = new HamburgerIngredients();
                relation.setHamburger(newHamburger);
                relation.setIngredient(ingredient);
                
                ingredientRelations.add(relation);
            }
            

            hamburgerRepository.save(newHamburger);

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

        @PutMapping("/{id}")
        public ResponseEntity<String> updateHamburger(
            @PathVariable String id,
            @RequestBody @Validated RequestHamburger request
        ) {
            try {
                hamburgerService.updateHamburger(id, request);
                return ResponseEntity.ok("Hamburger updated successfully!");
            } catch (ResourceNotFoundException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
            } catch (IllegalArgumentException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("An unexpected error occurred. Please, try again later!");
            }
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
