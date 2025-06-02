package com.dealuni.demo.dto;

import com.dealuni.demo.models.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

public class DiscountRequest {

    @Pattern(
            regexp = "^[\\p{L}0-9 .,!%&()\\-]{3,150}$",
            message = "Titlul discountului trebuie să conțină între 3 și 150 de caractere: litere, cifre, " +
                    "spații și simboluri uzuale."
    )
    @Column(length = 150)
    @NotNull(message = "Titlul discountului nu poate fi null.")
    private String title;

    @Pattern(
            regexp = "^[\\p{L}0-9.,!?%:;\"'()\\-\\/\\s]{10,600}$",
            message = "Descrierea trebuie să conțină între 10 și 600 de caractere și poate include litere, cifre, " +
                    "spații și semne de punctuație uzuale."
    )
    @Column(length = 600)
    private String description;

    @Min(value = 1, message = "Reducerea trebuie să fie de cel puțin 1%.")
    @Max(value = 100, message = "Reducerea nu poate depăși 100%.")
    @Column(nullable = false)
    private Integer percentage;

    @Pattern(
            regexp = "^[A-ZĂÂÎȘȚ][a-zăâîșțA-ZĂÂÎȘȚ\\- ]{1,49}$",
            message = "Numele orașului trebuie să înceapă cu literă mare și să conțină doar litere, " +
                    "spații sau cratimă."
    )

    //minim un oras in set
    @Size(min = 1)
    //discount-ul poate sa aiba unul sau mai multe orase
    //orasele se salveaza intr-un table pentru orase
    @ElementCollection(fetch = FetchType.EAGER)
    //un oras se salveaza ca un string
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "cities",
            joinColumns = @JoinColumn(name = "discount_id")
    )
    @Column(name = "category_enum")
    private Set<Category> cities;

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

    /*
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false)
    @NotNull(message = "Creatorul discountului nu poate fi null.")
    private User createdBy;

    //un discount poate sa fie numai de la o companie, o companie poate sa aibe multe discounturi
    @ManyToOne(fetch = FetchType.EAGER)
    //company_id este foreign key, face referinta la id din modelul Company
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @NotNull(message = "ID-ul companiei nu poate fi null.")
    private Company company;
    */

    public DiscountRequest(String title, String description, Integer percentage, Set<Category> cities, LocalDate validUntil, Category category, String logo, String code) {
        this.title = title;
        this.description = description;
        this.percentage = percentage;
        this.cities = cities;
        this.validUntil = validUntil;
        this.category = category;
        this.logo = logo;
        this.code = code;
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

    public Set<Category> getCities() {
        return cities;
    }

    public void setCities(Set<Category> cities) {
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
}
