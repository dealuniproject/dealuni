package com.dealuni.demo.dto;

import com.dealuni.demo.models.Category;
import com.dealuni.demo.models.City;
import java.time.LocalDate;
import java.util.Set;

public class DiscountResponse {

    private Long id;
    private String title;
    private String description;
    private Integer percentage;
    private Set<City> cities;
    private LocalDate validUntil;
    private LocalDate createdAt;
    private Category category;
    private String logo;
    private String code;
    private Long createdBy;
    private Long company;

    public DiscountResponse(Long id, String title, String description, Integer percentage, Set<City> cities, LocalDate validUntil, LocalDate createdAt, Category category, String logo, String code, Long createdBy, Long company) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.percentage = percentage;
        this.cities = cities;
        this.validUntil = validUntil;
        this.createdAt = createdAt;
        this.category = category;
        this.logo = logo;
        this.code = code;
        this.createdBy = createdBy;
        this.company = company;
    }

    public DiscountResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
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
