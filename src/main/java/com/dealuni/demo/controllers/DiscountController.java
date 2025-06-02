package com.dealuni.demo.controllers;

import com.dealuni.demo.dto.DiscountRequest;
import com.dealuni.demo.dto.DiscountResponse;
import com.dealuni.demo.services.DiscountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/discounts")
public class DiscountController {

    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<DiscountResponse> createNewDiscount(@Valid @RequestBody DiscountRequest discountRequest, @PathVariable Long userId) {
        DiscountResponse discountResponse = discountService.createNewDiscount(discountRequest, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(discountResponse);
    }

    @GetMapping
    public ResponseEntity<List<DiscountResponse>> getAllDiscounts() {
        List<DiscountResponse> discountResponseList = discountService.getAllExistingDiscounts();
        return ResponseEntity.ok(discountResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscountResponse> getDiscountById(@PathVariable Long id) {
        DiscountResponse discountResponse = discountService.getDiscountById(id);
        return ResponseEntity.ok(discountResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DiscountResponse> updateDiscountById(@PathVariable Long id, @RequestBody DiscountRequest discountRequest) {
        DiscountResponse discountResponse = discountService.updateDiscountById(id, discountRequest);
        return ResponseEntity.ok(discountResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscountById(@PathVariable Long id) {
        discountService.deleteDiscountById(id);
        return ResponseEntity.noContent().build();
    }
}

