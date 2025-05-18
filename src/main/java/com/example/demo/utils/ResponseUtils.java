package com.example.demo.utils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.exceptions.InvalidItemCodeException;
import com.example.demo.exceptions.ResourceNotFoundException;

public class ResponseUtils {

    public static <T> ResponseEntity<List<T>> getListResponse(Supplier<List<T>> supplier, String contextName) {
        try {
            List<T> list = supplier.get();

            return ResponseEntity
            .status(HttpStatus.OK)
            .body(list);
        } catch(Exception error) {
            System.out.println("Failed to get all " + contextName + ": " + error.getMessage());

            return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Collections.emptyList());
        }
    }

    public static <T, ID> ResponseEntity<T> getOneResponse(
        ID id,
        Function<ID, Optional<T>> findByIdFunction,
        String entityName
    ) throws ResourceNotFoundException {

        T entity = findByIdFunction.apply(id)
            .orElseThrow(() -> new ResourceNotFoundException(entityName + " " + id + " not found!"));

        return ResponseEntity.status(HttpStatus.OK).body(entity);
    }

    public static <R, E> ResponseEntity<String> registerNewEntity(
        R requestObject,
        Function<R, E> mapper,
        Consumer<E> saveFunction,
        String entityName,
        Function<R, String> codeExtractor,
        Function<String, Boolean> codeExistsChecker
    ) throws InvalidItemCodeException {
        try {
            if (codeExtractor != null && codeExistsChecker != null) {
                String code = codeExtractor.apply(requestObject);
                if (code != null && codeExistsChecker.apply(code)) {
                    throw new InvalidItemCodeException(entityName + " with code \"" + code + "\" already exists!");
                }
            }

            E newEntity = mapper.apply(requestObject);
            saveFunction.accept(newEntity);

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(entityName + " created successfully!");
        } catch (InvalidItemCodeException e) {
            throw e;
        } catch (Exception error) {
            System.out.println("Failed to register " + entityName + ": " + error.getMessage());

            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred. Please, try again later!");
        }
    }

    public static <R, E> ResponseEntity<E> updateEntity(
        R requestObject,
        Supplier<Optional<E>> findEntityById,
        BiConsumer<R, E> updateFunction,
        Consumer<E> saveFunction,
        String entityName,
        String id,
        Function<R, String> codeExtractor,
        Function<String, Boolean> codeExistsChecker,
        Function<E, String> currentEntityCodeExtractor
    ) throws ResourceNotFoundException, InvalidItemCodeException {
        E entity = findEntityById.get()
            .orElseThrow(() -> new ResourceNotFoundException(entityName + " " + id + " not found!"));

        if (codeExtractor != null && codeExistsChecker != null && currentEntityCodeExtractor != null) {
            String newCode = codeExtractor.apply(requestObject);
            String currentCode = currentEntityCodeExtractor.apply(entity);

            if (newCode != null && !newCode.equals(currentCode) && codeExistsChecker.apply(newCode)) {
                throw new InvalidItemCodeException(entityName + " with code \"" + newCode + "\" already exists!");
            }
        }

        updateFunction.accept(requestObject, entity);
        saveFunction.accept(entity);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(entity);
    }

    public static <E> ResponseEntity<String> deleteEntityResponse(
        Supplier<Optional<E>> findEntityById,
        Consumer<E> deleteFunction,
        String entityName,
        String id
    ) throws ResourceNotFoundException {
        E entity = findEntityById.get()
        .orElseThrow(
            () -> new ResourceNotFoundException(entityName + " " + id + " not found!")
        );

        deleteFunction.accept(entity);

        return ResponseEntity
        .status(HttpStatus.OK)
        .body(entityName + " deleted successfully!");
    }

}
