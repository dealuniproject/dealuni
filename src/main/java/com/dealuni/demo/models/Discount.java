package com.dealuni.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "discounts")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(
            regexp = "^[\\p{L}0-9 .,!%&()\\-]{3,150}$",
            message = "Numele discountului trebuie să conțină între 3 și 150 de caractere: litere, cifre, spații și " +
                    "simboluri uzuale (.,!%&()-)"
    )
    @Column(nullable = false, length = 150)
    private String name;

    @Pattern(
            regexp = "^[\\p{L}0-9.,!?%:;\"'()\\-\\/\\s]{10,600}$",
            message = "Descrierea trebuie să conțină între 10 și 600 de caractere și poate include litere, cifre, " +
                    "spații și semne de punctuație uzuale"
    )
    @Column(nullable = false, length = 600)
    private String description;

    @Min(value = 1, message = "Reducerea trebuie să fie de cel puțin 1%")
    @Max(value = 100, message = "Reducerea nu poate depăși 100%")
    @Column(nullable = false)
    private Integer percentage;

    @Pattern(
            regexp = "^[A-ZĂÂÎȘȚ][a-zăâîșțA-ZĂÂÎȘȚ\\- ]{1,49}$",
            message = "Numele orașului trebuie să înceapă cu literă mare și să conțină doar litere, spații sau cratimă"
    )
    @Column(nullable = false, length = 50)
    private String city;

    @Future(message = "Data de expirare trebuie să fie în viitor")
    @Column(nullable = false)
    private LocalDate validUntil;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @NotNull(message = "Categoria este obligatorie")
    private Category category;

    @Column(nullable = false, length = 200)
    @NotNull(message = "Logo-ul companiei este obligatoriu")
    private String logo;

    @Column(length = 20)
    private String code;

    //un discount poate sa fie numai de la o companie, o companie poate sa aibe multe discounturi
    @ManyToOne(fetch = FetchType.EAGER)
    //company_id este foreign key, face referinta la id din modelul Company
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @NotNull(message = "ID-ul companiei nu poate fi null")
    private Company company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}


