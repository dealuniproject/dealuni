package com.dealuni.demo.dto;

import com.dealuni.demo.models.Category;
import com.dealuni.demo.models.Company;
import com.dealuni.demo.models.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class DiscountResponse {

    private Long id;
    private String title;
    private String description;
    private Integer percentage;
    private Set<Category> categories;
    private LocalDate validUntil;
    private LocalDateTime createdAt;
    private Category category;
    private String logo;
    private String code;
    private User createdBy;
    private Company company;

    public DiscountResponse(Long id, String title, String description, Integer percentage, Set<Category> categories, LocalDate validUntil, LocalDateTime createdAt, Category category, String logo, String code, User createdBy, Company company) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.percentage = percentage;
        this.categories = categories;
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

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDate validUntil) {
        this.validUntil = validUntil;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
