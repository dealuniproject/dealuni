package com.dealuni.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "discounts")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[\\p{L}\\p{N}\\p{P}\\p{Zs}]{3,150}$", message = "Titlul discountului trebuie să conțină între 3 și 150 de caractere: litere, cifre, spații și simboluri uzuale.")
    @Column(length = 150)
    @NotNull(message = "Titlul discountului nu poate fi null.")
    private String title;

    @Pattern(regexp = "^[\\p{L}\\p{N}\\p{P}\\p{Zs}]{10,600}$", message = "Descrierea trebuie să conțină între 10 și 600 de caractere și poate include litere, cifre, spații și semne de punctuație uzuale.")
    @Column(length = 600)
    private String description;

    @Min(value = 1, message = "Reducerea trebuie să fie de cel puțin 1%.")
    @Max(value = 100, message = "Reducerea nu poate depăși 100%.")
    @Column(nullable = false)
    private Integer percentage;

    @NotNull(message = "Lista de orașe nu poate fi null.")
    @Size(min = 1, message = "Lista de orașe trebuie să conțină cel puțin un oraș.")
    //discount-ul poate sa aiba unul sau mai multe orase
    //orasele se salveaza intr-un table pentru orase
    @ElementCollection(fetch = FetchType.EAGER)
    //un oras se salveaza ca un string
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "cities", joinColumns = @JoinColumn(name = "discount_id"))
    @Column(name = "city_enum")
    private Set<City> cities;

    @Future(message = "Data de expirare trebuie să fie în viitor.")
    @Column(nullable = false)
    private LocalDate validUntil;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDate createdAt;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Categoria este obligatorie.")
    private Category category;

    @Column(nullable = false, length = 200)
    @NotNull(message = "Logo-ul companiei este obligatoriu.")
    private String logo;

    @Column(length = 20)
    private String code;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false)
    @NotNull(message = "Creatorul discountului e obligatoriu.")
    private User createdBy;

    //un discount poate sa fie numai de la o companie, o companie poate sa aibe multe discounturi
    @ManyToOne(fetch = FetchType.EAGER)
    //company_id este foreign key, face referinta la id din modelul Company
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @NotNull(message = "ID-ul companiei este obligatoriu.")
    private Company company;

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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}


