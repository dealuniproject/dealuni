package com.dealuni.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[\\p{L}0-9][\\p{L}0-9\\-\\.\\&\\,\\(\\) ]{2,99}$", message = "Numele companiei trebuie să înceapă cu literă sau cifră și poate conține doar litere, cifre, cratimă, punct, virgulă, & sau paranteze.")
    @Column(nullable = false, length = 100)
    @NotNull(message = "Numele companiei este obligatoriu.")
    private String name;


    @Pattern(regexp = "^[\\p{L}0-9.,!?%:;\"'()\\-\\/\\s]{10,600}$", message = "Descrierea trebuie să conțină între 10 și 600 de caractere și poate include litere, cifre, spații și semne de punctuație uzuale.")
    @Column(length = 600)
    @NotNull(message = "Descrierea este obligatorie.")
    private String description;

    @Column(nullable = false, length = 200)
    @NotNull(message = "Logo-ul companiei este obligatoriu.")
    private String logo;

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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
