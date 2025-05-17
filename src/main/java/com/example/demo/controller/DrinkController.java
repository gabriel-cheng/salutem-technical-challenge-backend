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
import com.example.demo.domain.drink.Drink;
import com.example.demo.domain.drink.DrinkRepository;
import com.example.demo.domain.drink.RequestDrink;
import com.example.demo.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping("/drink")
public class DrinkController {

    @Autowired
    private DrinkRepository drinkRepository;

    @GetMapping
    public ResponseEntity<List<Drink>> getAllDrinks() {
        return ResponseUtils.getListResponse(
            drinkRepository::findAll,
            "Drink"
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Drink> getOneDrink(@PathVariable String id) throws ResourceNotFoundException {
        return ResponseUtils.getOneResponse(
            id,
            drinkRepository::findById,
            "Drink"
        );
    }

    @PostMapping
    public ResponseEntity<String> registerNewDrink(
        @RequestBody @Validated RequestDrink drink
    ) {
        return ResponseUtils.registerNewEntity(
            drink,
            req -> new Drink(req),
            drinkRepository::save,
            "Drink"
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Drink> updateDrink(
        @RequestBody @Validated RequestDrink drink,
        @PathVariable String id
    ) throws ResourceNotFoundException {
        return ResponseUtils.updateEntity(
            drink,
            () -> drinkRepository.findById(id),
            (request, entity) -> {
                entity.setCode(request.code());
                entity.setDescription(request.description());
                entity.setUnity_price(request.unity_price());
                entity.setSugar_flag(request.sugar_flag());
            },
            drinkRepository::save,
            "Drink",
            id
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDrink(
        @PathVariable String id
    ) throws ResourceNotFoundException {
        return ResponseUtils.deleteEntityResponse(
            () -> drinkRepository.findById(id),
            drinkRepository::delete,
            "Drink",
            id
        );
    }

}
