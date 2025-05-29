package com.dealuni.demo.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;

public class UserRequest {

    @Column(unique = true, nullable = false, length = 60)
    @Pattern(regexp = "^[a-zA-Z0-9._-]+@(stud\\.upb\\.ro|student\\.utcb\\.ro|stud\\.usamv\\.ro|stud\\.unibuc\\.ro|stud\\.umfcd\\.ro|student\\.ase\\.ro|student\\.snspa\\.ro|stud\\.utcluj\\.ro|stud\\.usamvcluj\\.ro|stud\\.ubbcluj\\.ro|stud\\.umfcluj\\.ro|student\\.uaic\\.ro|student\\.umfiasi\\.ro|student\\.upt\\.ro|student\\.usvt\\.ro|e-uvt\\.ro|student\\.umft\\.ro|student\\.uoradea\\.ro|edu\\.ucv\\.ro|student\\.umfst\\.ro|uab\\.ro|uav\\.ro|ub\\.ro|unitbv\\.ro|univ-ovidius\\.ro|cmu-edu\\.eu|ugal\\.ro|upg-ploiesti\\.ro|ulbsibiu\\.ro|usv\\.ro|valahia\\.ro|utgjiu\\.ro|anmb\\.ro|afahc\\.ro|aft\\.ro)$",
            message = "Emailul trebuie să aparțină unui domeniu instituțional valid")
    private String username;

    @Pattern(
            regexp = "^[A-ZĂÂÎȘȚ][a-zăâîșțA-ZĂÂÎȘȚ\\- ]{1,49}$",
            message = "Prenumele trebuie să înceapă cu literă mare și să conțină doar litere, cratimă sau spațiu"
    )
    @Column(nullable = false, length = 50)
    private String firstName;

    @Pattern(
            regexp = "^[A-ZĂÂÎȘȚ][a-zăâîșțA-ZĂÂÎȘȚ\\- ]{1,49}$",
            message = "Numele trebuie să înceapă cu literă mare și să conțină doar litere, cratimă sau spațiu"
    )
    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(unique = true, nullable = false, length = 100)
    private String universityName;

    @Column(nullable = false, length = 50)
    @Pattern(regexp = "^(?=.*[A-Z]).{8,}$",
            message = "Parola trebuie să aibă cel puțin 8 caractere și să conțină cel puțin o literă mare.")
    private String password;

    public UserRequest(String username, String firstName, String lastName, String universityName, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.universityName = universityName;
        this.password = password;
    }

    public UserRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
