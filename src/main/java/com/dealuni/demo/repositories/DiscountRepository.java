package com.dealuni.demo.repositories;

import com.dealuni.demo.models.Category;
import com.dealuni.demo.models.City;
import com.dealuni.demo.models.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiscountRepository extends JpaRepository<Discount, Long> {

    @Query("SELECT d FROM Discount d WHERE " +
            "(:city IS NULL OR :city IN elements(d.cities)) AND " +
            "(:category IS NULL OR d.category = :category)")
    List<Discount> findByCityAndCategory(@Param("city") City city, @Param("category") Category category);
}
