package com.dealuni.demo.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;

public class CompanyRequest {
    @Pattern(regexp = "^[\\p{L}\\p{N}\\p{P}\\p{Zs}]{2,100}$", message = "Numele companiei trebuie să înceapă cu literă sau cifră și poate conține doar litere, cifre, " + "cratimă, punct, virgulă, & sau paranteze.")
    @Column(nullable = false, length = 100)
    private String name;

    @Pattern(regexp = "^[\\p{L}\\p{N}\\p{P}\\p{Zs}]{10,600}$", message = "Descrierea trebuie să conțină între 10 și 600 de caractere și poate include litere, cifre, " + "spații și semne de punctuație uzuale.")
    @Column(length = 600)
    private String description;

    @Column(nullable = false, length = 200)
    private String logo;

    public CompanyRequest(String name, String description, String logo) {
        this.name = name;
        this.description = description;
        this.logo = logo;
    }

    public CompanyRequest() {
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
