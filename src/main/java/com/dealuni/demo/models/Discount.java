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
            message = "Numele discountului trebuie să conțină între 3 și 150 de caractere: litere, cifre, spații și simboluri uzuale (.,!%&()-)"
    )
    @Column(nullable = false, length = 150)
    private String name;

    @Pattern(
            regexp = "^[\\p{L}0-9.,!?%:;\"'()\\-\\/\\s]{10,600}$",
            message = "Descrierea trebuie să conțină între 10 și 600 de caractere și poate include litere, cifre, spații și semne de punctuație uzuale"
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
    private String logo;

    @Column(length = 20)
    private String code;


}


