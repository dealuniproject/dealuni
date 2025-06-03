package com.dealuni.demo.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AuthRequest {

    @NotBlank
    @Column(unique = true, nullable = false, length = 60)
    @Pattern(regexp = "^[a-zA-Z0-9._-]+@(stud\\.upb\\.ro|student\\.utcb\\.ro|stud\\.usamv\\.ro|stud\\.unibuc\\.ro|stud\\.umfcd\\.ro|student\\.ase\\.ro|student\\.snspa\\.ro|stud\\.utcluj\\.ro|stud\\.usamvcluj\\.ro|stud\\.ubbcluj\\.ro|stud\\.umfcluj\\.ro|student\\.uaic\\.ro|student\\.umfiasi\\.ro|student\\.upt\\.ro|student\\.usvt\\.ro|e-uvt\\.ro|student\\.umft\\.ro|student\\.uoradea\\.ro|edu\\.ucv\\.ro|student\\.umfst\\.ro|uab\\.ro|uav\\.ro|ub\\.ro|unitbv\\.ro|univ-ovidius\\.ro|cmu-edu\\.eu|ugal\\.ro|upg-ploiesti\\.ro|ulbsibiu\\.ro|usv\\.ro|valahia\\.ro|utgjiu\\.ro|anmb\\.ro|afahc\\.ro|aft\\.ro)$",
            message = "Emailul trebuie să aparțină unui domeniu instituțional valid")
    private String username;

    @NotBlank
    @Column(nullable = false, length = 50)
    @Pattern(regexp = "^(?=.*[A-Z]).{8,}$",
            message = "Parola trebuie să aibă cel puțin 8 caractere și să conțină cel puțin o literă mare.")
    private String password;

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}