package com.dealuni.demo.repositories;

import com.dealuni.demo.models.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
