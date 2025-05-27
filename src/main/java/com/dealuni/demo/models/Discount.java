package com.dealuni.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "discounts")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(
            regexp = "^[\\p{L}0-9 .,!%&()\\-]{3,150}$",
            message = "Titlul discountului trebuie să conțină între 3 și 150 de caractere: litere, cifre, " +
                    "spații și simboluri uzuale (.,!%&()-)"
    )
    @Column(length = 150)
    @NotNull(message = "Titlul discountului nu poate fi null")
    private String title;

    @Pattern(
            regexp = "^[\\p{L}0-9.,!?%:;\"'()\\-\\/\\s]{10,600}$",
            message = "Descrierea trebuie să conțină între 10 și 600 de caractere și poate include litere, " +
                    "cifre, spații și semne de punctuație uzuale"
    )
    @Column(length = 600)
    private String description;

    @Min(value = 1, message = "Reducerea trebuie să fie de cel puțin 1%")
    @Max(value = 100, message = "Reducerea nu poate depăși 100%")
    @Column(nullable = false)
    private Integer percentage;

    @Pattern(
            regexp = "^[A-ZĂÂÎȘȚ][a-zăâîșțA-ZĂÂÎȘȚ\\- ]{1,49}$",
            message = "Numele orașului trebuie să înceapă cu literă mare și să conțină doar litere, " +
                    "spații sau cratimă"
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
    private Set<Category> categories;

    @Future(message = "Data de expirare trebuie să fie în viitor")
    @Column(nullable = false)
    private LocalDate validUntil;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    @NotNull(message = "Categoria este obligatorie")
    private Category category;

    @Column(nullable = false, length = 200)
    private String logo;

    @Column(length = 20)
    private String code;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false)
    @NotNull(message = "Creatorul discountului nu poate fi null")
    private User createdBy;
}


