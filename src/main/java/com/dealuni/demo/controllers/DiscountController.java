package com.dealuni.demo.controllers;

import com.dealuni.demo.dto.DiscountRequest;
import com.dealuni.demo.dto.DiscountResponse;
import com.dealuni.demo.models.Category;
import com.dealuni.demo.models.City;
import com.dealuni.demo.models.CustomUserDetails;
import com.dealuni.demo.services.DiscountService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/discounts")
public class DiscountController {

    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @PreAuthorize("hasRole('ADMIN')")
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
    public ResponseEntity<DiscountResponse> getDiscountById(@PathVariable Long id, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        if (!userDetails.isVerified()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        DiscountResponse discountResponse = discountService.getDiscountById(id);
        return ResponseEntity.ok(discountResponse);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<DiscountResponse>> getDiscountsByCityAndCategory(@RequestParam(required = false) City city, @RequestParam(required = false) Category category) {

        List<DiscountResponse> filteredDiscounts = discountService.getDiscountsByCityAndCategory(city, category);
        return ResponseEntity.ok(filteredDiscounts);
    }

    @GetMapping("/search")
    public ResponseEntity<List<DiscountResponse>> searchDiscounts(@RequestParam(name = "name", required = false) String name, @RequestParam(name = "minPercentage", required = false) Integer minPercentage, @RequestParam(name = "maxPercentage", required = false) Integer maxPercentage, @RequestParam(name = "city", required = false) City city, @RequestParam(name = "category", required = false) Category category, @RequestParam(name = "maxValidUntil", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate maxValidUntil) {

        List<DiscountResponse> searchResults = discountService.searchDiscounts(name, minPercentage, maxPercentage, city, category, maxValidUntil);
        return ResponseEntity.ok(searchResults);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<DiscountResponse> updateDiscountById(@PathVariable Long id, @RequestBody DiscountRequest discountRequest) {
        DiscountResponse discountResponse = discountService.updateDiscountById(id, discountRequest);
        return ResponseEntity.ok(discountResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscountById(@PathVariable Long id) {
        discountService.deleteDiscountById(id);
        return ResponseEntity.noContent().build();
    }
}

