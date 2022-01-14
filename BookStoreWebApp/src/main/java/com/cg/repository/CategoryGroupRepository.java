package com.cg.repository;

import com.cg.model.Category;
import com.cg.model.CategoryGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryGroupRepository extends JpaRepository<CategoryGroup, Long> {

    Boolean existsBySlugEqualsAndCategoryEquals(String slug, Category category);

    Boolean existsBySlugEqualsAndCategoryEqualsAndIdIsNot(String slug, Category category, Long id);
}
