package com.cg.service.categorygroup;

import com.cg.model.Category;
import com.cg.model.CategoryGroup;
import com.cg.service.IGeneralService;

public interface CategoryGroupService extends IGeneralService<CategoryGroup> {

    Boolean existsBySlugEqualsAndCategoryEquals(String slug, Category category);

    Boolean existsBySlugEqualsAndCategoryEqualsAndIdIsNot(String slug, Category category, Long id);
}
