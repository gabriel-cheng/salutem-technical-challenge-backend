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

import com.example.demo.domain.ingredient.Ingredient;
import com.example.demo.domain.ingredient.IngredientRepository;
import com.example.demo.domain.ingredient.RequestIngredient;
import com.example.demo.exceptions.InvalidItemCodeException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.utils.ResponseUtils;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        return ResponseUtils.getListResponse(
            ingredientRepository::findAll,
            "Ingredient"
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getOneIngredient(@PathVariable String id) throws ResourceNotFoundException {
        return ResponseUtils.getOneResponse(
            id,
            ingredientRepository::findById,
            "Ingredient"
        );
    }

    @PostMapping
    public ResponseEntity<String> registerNewIngredient(
        @RequestBody @Validated RequestIngredient ingredient
    ) throws InvalidItemCodeException {
        return ResponseUtils.registerNewEntity(
            ingredient,
            req -> new Ingredient(req),
            ingredientRepository::save,
            "Ingredient",
            RequestIngredient::code,
            code -> ingredientRepository.existsByCode(code)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> updateIngredient(
        @RequestBody @Validated RequestIngredient ingredient,
        @PathVariable String id
    ) throws ResourceNotFoundException, InvalidItemCodeException {
        return ResponseUtils.updateEntity(
            ingredient,
            () -> ingredientRepository.findById(id),
            (request, entity) -> {
                entity.setCode(request.code());
                entity.setDescription(request.description());
                entity.setUnity_price(request.unity_price());
                entity.setAdditional_flag(request.additional_flag());
            },
            ingredientRepository::save,
            "Ingredient",
            id,
            RequestIngredient::code,
            code -> ingredientRepository.existsByCodeAndIngredientIdNot(code, id),
            entity -> entity.getCode()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteIngredient(@PathVariable String id) throws ResourceNotFoundException {
        return ResponseUtils.deleteEntityResponse(
            () -> ingredientRepository.findById(id),
            ingredientRepository::delete,
            "Ingredient",
            id
        );
    }

}
