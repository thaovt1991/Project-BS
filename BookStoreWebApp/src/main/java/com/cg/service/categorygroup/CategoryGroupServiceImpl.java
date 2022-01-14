package com.cg.service.categorygroup;

import com.cg.model.Category;
import com.cg.model.CategoryGroup;
import com.cg.repository.CategoryGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CategoryGroupServiceImpl implements CategoryGroupService {

    @Autowired
    private CategoryGroupRepository categoryGroupRepository;

    @Override
    public List<CategoryGroup> findAll() {
        return categoryGroupRepository.findAll();
    }

    @Override
    public Optional<CategoryGroup> findById(Long id) {
        return categoryGroupRepository.findById(id);
    }

    @Override
    public CategoryGroup getById(Long id) {
        return categoryGroupRepository.getById(id);
    }

    @Override
    public Boolean existsBySlugEqualsAndCategoryEquals(String slug, Category category) {
        return categoryGroupRepository.existsBySlugEqualsAndCategoryEquals(slug, category);
    }

    @Override
    public Boolean existsBySlugEqualsAndCategoryEqualsAndIdIsNot(String slug, Category category, Long id) {
        return categoryGroupRepository.existsBySlugEqualsAndCategoryEqualsAndIdIsNot(slug, category, id);
    }

    @Override
    public CategoryGroup save(CategoryGroup categoryGroup) {
        return categoryGroupRepository.save(categoryGroup);
    }

    @Override
    public void remove(Long id) {

    }
}
