package com.dealuni.demo.controllers;

import com.dealuni.demo.dto.CompanyRequest;
import com.dealuni.demo.dto.CompanyResponse;
import com.dealuni.demo.services.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CompanyResponse> createNewCompany(@Valid @RequestBody CompanyRequest companyRequest) {
        CompanyResponse companyResponse = companyService.createNewCompany(companyRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(companyResponse);
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAllCompanies() {
        List<CompanyResponse> companyResponseList = companyService.getAllExistingCompanies();
        return ResponseEntity.ok(companyResponseList);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getCompanyById(@PathVariable Long id) {
        CompanyResponse companyResponse = companyService.getCompanyById(id);
        return ResponseEntity.ok(companyResponse);
    }

    @GetMapping("/by-discount/{discountId}")
    public ResponseEntity<CompanyResponse> getCompanyByDiscountId(@PathVariable Long discountId) {
        CompanyResponse companyResponse = companyService.getCompanyByDiscountId(discountId);
        return ResponseEntity.ok(companyResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<CompanyResponse> updateCompany(@PathVariable Long id, @Valid @RequestBody CompanyRequest companyRequest) {
        CompanyResponse companyResponse = companyService.updateCompanyById(id, companyRequest);
        return ResponseEntity.ok(companyResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompanyById(@PathVariable Long id) {
        companyService.deleteCompanyById(id);
        return ResponseEntity.noContent().build();
    }
}
