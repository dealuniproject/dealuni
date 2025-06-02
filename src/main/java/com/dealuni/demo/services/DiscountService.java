package com.dealuni.demo.services;

import com.dealuni.demo.dto.*;
import com.dealuni.demo.models.*;
import com.dealuni.demo.repositories.CompanyRepository;
import com.dealuni.demo.repositories.DiscountRepository;
import com.dealuni.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public DiscountService(DiscountRepository discountRepository, UserRepository userRepository, CompanyRepository companyRepository) {
        this.discountRepository = discountRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }

    public DiscountResponse createNewDiscount(DiscountRequest discountRequest, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User-ul nu a fost găsit."));
        Company company = companyRepository.findById(discountRequest.getCompany())
                .orElseThrow(() -> new NoSuchElementException("Compania nu a fost găsită."));
        validateDiscount(discountRequest);
        Discount discount = convertDiscountRequestToDiscountEntity(discountRequest);
        discount.setCompany(company);
        discount.setCreatedBy(user);
        Discount savedDiscount = discountRepository.save(discount);
        return convertDiscountEntityToDiscountResponse(savedDiscount);
    }

    public List<DiscountResponse> getAllExistingDiscounts() {
        List<Discount> allExistingDiscounts = discountRepository.findAll();
        return convertDiscountEntityToDiscountResponse(allExistingDiscounts);
    }

    public DiscountResponse getDiscountById(Long id) {
        Discount discount = discountRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Discount-ul nu a fost găsit."));
        return convertDiscountEntityToDiscountResponse(discount);
    }

    public DiscountResponse updateDiscountById(Long id, DiscountRequest discountRequest) {
        Discount existingDiscount = discountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discount-ul nu a fost găsit."));

        if (discountRequest.getTitle() != null) {
            existingDiscount.setTitle(discountRequest.getTitle());
        }

        if (discountRequest.getDescription() != null) {
            existingDiscount.setDescription(discountRequest.getDescription());
        }

        if (discountRequest.getPercentage() != null) {
            existingDiscount.setPercentage(discountRequest.getPercentage());
        }

        if (discountRequest.getCities() != null) {
            existingDiscount.setCities(discountRequest.getCities());
        }

        if (discountRequest.getValidUntil() != null) {
            existingDiscount.setValidUntil(discountRequest.getValidUntil());
        }

        if (discountRequest.getCategory() != null) {
            existingDiscount.setCategory(discountRequest.getCategory());
        }

        if (discountRequest.getLogo() != null) {
            existingDiscount.setLogo(discountRequest.getLogo());
        }

        if (discountRequest.getCode() != null) {
            existingDiscount.setCode(discountRequest.getCode());
        }

        Discount updatedDiscount = discountRepository.save(existingDiscount);
        return convertDiscountEntityToDiscountResponse(updatedDiscount);
    }

    public void deleteDiscountById(Long id) {
        discountRepository.deleteById(id);
    }

    public List<DiscountResponse> convertDiscountEntityToDiscountResponse(List<Discount> discounts) {
        List<DiscountResponse> discountResponses = new ArrayList<>();
        for (Discount discount : discounts) {
            discountResponses.add(convertDiscountEntityToDiscountResponse(discount));
        }
        return discountResponses;
    }

    public DiscountResponse convertDiscountEntityToDiscountResponse(Discount discount) {
        DiscountResponse discountResponse = new DiscountResponse();
        discountResponse.setId(discount.getId());
        discountResponse.setTitle(discount.getTitle());
        discountResponse.setDescription(discount.getDescription());
        discountResponse.setPercentage(discount.getPercentage());
        discountResponse.setCities(discount.getCities());
        discountResponse.setValidUntil(discount.getValidUntil());
        discountResponse.setCreatedAt(discount.getCreatedAt());
        discountResponse.setCategory(discount.getCategory());
        discountResponse.setLogo(discount.getLogo());
        discountResponse.setCode(discount.getCode());
        discountResponse.setCreatedBy(discount.getCreatedBy().getId());
        discountResponse.setCompany(discount.getCompany().getId());
        return discountResponse;
    }

    public Discount convertDiscountRequestToDiscountEntity(DiscountRequest discountRequest) {
        Discount discount = new Discount();
        discount.setTitle(discountRequest.getTitle());
        discount.setDescription(discountRequest.getDescription());
        discount.setPercentage(discountRequest.getPercentage());
        discount.setCities(discountRequest.getCities());
        discount.setValidUntil(discountRequest.getValidUntil());
        discount.setCategory(discountRequest.getCategory());
        discount.setLogo(discountRequest.getLogo());
        discount.setCode(discountRequest.getCode());
        return discount;
    }

    public void validateDiscount(DiscountRequest discountRequest) {

        if (discountRequest.getTitle() == null || discountRequest.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Numele discount-ului nu poate fi null.");
        }

        if (discountRequest.getTitle().length() > 150) {
            throw new IllegalArgumentException("Numele discount-ului nu poate depăși 150 de caractere.");
        }

        String regex = "^[\\p{L}0-9 .,!%&()\\-]{3,150}$";

        if (!discountRequest.getTitle().matches(regex)) {
            throw new IllegalArgumentException("Titlul discountului trebuie să conțină între 3 și 150 de caractere: litere, cifre, spații și simboluri uzuale.");
        }

        if (discountRequest.getDescription() == null || discountRequest.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Descrierea discount-ului nu poate fi null.");
        }

        if (discountRequest.getDescription().length() > 600) {
            throw new IllegalArgumentException("Descrierea discount-ului nu poate depăși 600 de caractere.");
        }

        if (!discountRequest.getDescription().matches("^[\\p{L}0-9.,!?%:;\"'()\\-\\/\\s]{10,600}$")) {
            throw new IllegalArgumentException("Descrierea trebuie să conțină între 10 și 600 de caractere și poate include litere, cifre, spații și semne de punctuație uzuale.");
        }

        if (discountRequest.getPercentage() == null) {
            throw new IllegalArgumentException("Procentajul discount-ului nu poate fi null.");
        }

        if (discountRequest.getPercentage() < 1) {
            throw new IllegalArgumentException("Discount-ul trebuie să fie de cel puțin 1%.");
        }

        if (discountRequest.getPercentage() > 100) {
            throw new IllegalArgumentException("Discount-ul nu poate depăși 100%.");
        }

        if (discountRequest.getCities() == null) {
            throw new IllegalArgumentException("Numele orașului nu poate fi null.");
        }

        if (discountRequest.getValidUntil() == null) {
            throw new IllegalArgumentException("Data de expirare nu poate fi null.");
        }

        if (!discountRequest.getValidUntil().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data de expirare trebuie să fie în viitor.");
        }

        if (discountRequest.getCategory() == null) {
            throw new IllegalArgumentException("Categoria nu poate fi null.");
        }

        if (discountRequest.getLogo() == null || discountRequest.getLogo().trim().isEmpty()) {
            throw new IllegalArgumentException("Logo-ul companiei este obligatoriu.");
        }

        if (discountRequest.getLogo().length() > 200) {
            throw new IllegalArgumentException("URL-ul logo-ului nu poate depăși 100 de caractere.");
        }
    }
}
