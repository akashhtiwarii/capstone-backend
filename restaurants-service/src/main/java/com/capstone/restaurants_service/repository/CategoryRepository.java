package com.capstone.restaurants_service.repository;

import com.capstone.restaurants_service.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    /**
     * find category by Id.
     * @param categoryId
     * @return category if found
     */
    Category findById(long categoryId);
    /**
     * find category by name.
     * @param name
     * @return category if found
     */
    Category findByName(String name);
}
