package com.cg.service.category;

import com.cg.model.Category;
import com.cg.model.dto.CategoryDTO;
import com.cg.service.IGeneralService;

public interface CategoryService extends IGeneralService<Category> {

    Boolean existsBySlugEquals(String slug);

    Boolean existsBySlugEqualsAndIdIsNot(String slug, long id);


    Iterable<Category> findAllByDeletedIsFalse();

    Category create(CategoryDTO categoryDTO) ;
}
