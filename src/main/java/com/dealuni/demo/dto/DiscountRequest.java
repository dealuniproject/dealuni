package com.dealuni.demo.dto;

import com.dealuni.demo.models.Category;
import com.dealuni.demo.models.City;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Set;

public class DiscountRequest {

    @Pattern(regexp = "^[\\p{L}\\p{N}\\p{P}\\p{Zs}]{3,150}$", message = "Titlul discountului trebuie să conțină între 3 și 150 de caractere: litere, cifre, spații și simboluri uzuale.")
    @Column(length = 150)
    @NotNull(message = "Titlul discountului nu poate fi gol.")
    private String title;

    @Pattern(regexp = "^[\\p{L}\\p{N}\\p{P}\\p{Zs}]{10,600}$", message = "Descrierea trebuie să conțină între 10 și 600 de caractere și poate include litere, cifre, spații și semne de punctuație uzuale.")
    @Column(length = 600)
    private String description;

    @Min(value = 1, message = "Reducerea trebuie să fie de cel puțin 1%.")
    @Max(value = 100, message = "Reducerea nu poate depăși 100%.")
    @Column(nullable = false)
    private Integer percentage;

    @NotNull(message = "Lista de orașe nu poate fi goală.")
    @Size(min = 1, message = "Lista de orașe trebuie să conțină cel puțin un oraș.")
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "cities", joinColumns = @JoinColumn(name = "discount_id"))
    @Column(name = "city_enum")
    private Set<City> cities;

    @Future(message = "Data de expirare trebuie să fie în viitor.")
    @Column(nullable = false)
    private LocalDate validUntil;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Categoria este obligatorie.")
    private Category category;

    @Column(nullable = false, length = 200)
    @NotNull(message = "Logo-ul companiei este obligatoriu.")
    private String logo;

    @Column(length = 20)
    private String code;

    private Long createdBy;

    @NotNull(message = "ID-ul companiei nu poate fi null.")
    private Long company;

    public DiscountRequest(String title, String description, Integer percentage, Set<City> cities, LocalDate validUntil, Category category, String logo, String code, Long createdBy, Long company) {
        this.title = title;
        this.description = description;
        this.percentage = percentage;
        this.cities = cities;
        this.validUntil = validUntil;
        this.category = category;
        this.logo = logo;
        this.code = code;
        this.createdBy = createdBy;
        this.company = company;
    }

    public DiscountRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public Set<City> getCities() {
        return cities;
    }

    public void setCities(Set<City> cities) {
        this.cities = cities;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDate validUntil) {
        this.validUntil = validUntil;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getCompany() {
        return company;
    }

    public void setCompany(Long company) {
        this.company = company;
    }
}
