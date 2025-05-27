package com.dealuni.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 60)
    @Pattern(regexp = "^[a-zA-Z]+\\.[a-zA-Z]+@(stud\\.upb\\.ro|student\\.utcb\\.ro|stud\\.usamv\\.ro|stud\\.unibuc\\.ro" +
            "|stud\\.umfcd\\.ro|student\\.ase\\.ro|student\\.snspa\\.ro|stud\\.utcluj\\.ro|stud\\.usamvcluj\\.ro" +
            "|stud\\.ubbcluj\\.ro|stud\\.umfcluj\\.ro|student\\.uaic\\.ro|student\\.umfiasi\\.ro|student\\.upt\\.ro" +
            "|student\\.usvt\\.ro|student\\.uvt\\.ro|student\\.umft\\.ro|student\\.uoradea\\.ro|edu\\.ucv\\.ro" +
            "|student\\.umfst\\.ro|uab\\.ro|uav\\.ro|ub\\.ro|unitbv\\.ro|univ-ovidius\\.ro|cmu-edu\\.eu|ugal\\.ro" +
            "|upg-ploiesti\\.ro|ulbsibiu\\.ro|usv\\.ro|valahia\\.ro|utgjiu\\.ro|anmb\\.ro|afahc\\.ro|aft\\.ro)$",
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


    @Column(nullable = false, length = 50)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()\\-_=+{};:,<.>])(?=.{8,})" + ".*$",
            message = "Parola trebuie să aibă cel puțin 8 caractere și să conțină cel puțin o literă mare, " +
                    "o cifră și un caracter special.")
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Numele universității este obligatoriu")
    private University university;

    private Boolean isVerified = false;

    private Boolean isBlocked = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }
}
