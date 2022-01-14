package com.cg.controller.cp;

import com.cg.model.Category;
import com.cg.model.CategoryGroup;
import com.cg.service.category.CategoryService;
import com.cg.service.categorygroup.CategoryGroupService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cp/category-groups")
public class CategoryGroupCPController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryGroupService categoryGroupService;

    @GetMapping
    public ModelAndView showList() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("cp/category-group/list");

        List<CategoryGroup> categoryGroups = categoryGroupService.findAll();

        modelAndView.addObject("categoryGroups", categoryGroups);

        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreate() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("cp/category-group/create");

        List<Category> categories = categoryService.findAll();

        modelAndView.addObject("categories", categories);
        modelAndView.addObject("categoryGroup", new CategoryGroup());
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEdit(@PathVariable Long id) {

        ModelAndView modelAndView = new ModelAndView();

        Optional<CategoryGroup> categoryGroup = categoryGroupService.findById(id);

        if (categoryGroup.isPresent()) {
            Iterable<Category> categories = categoryService.findAllByDeletedIsFalse();
            modelAndView.addObject("categories", categories);
            modelAndView.addObject("categoryGroup", categoryGroup);

        } else {
            modelAndView.addObject("categoryGroup", new CategoryGroup());
            modelAndView.addObject("script", false);
            modelAndView.addObject("success", false);
            modelAndView.addObject("error", "Invalid category group information");

        }
        modelAndView.setViewName("cp/category-group/edit");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView showDelete(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("cp/category-group/delete");
        Optional<CategoryGroup> categoryGroup = categoryGroupService.findById(id);

        if (categoryGroup.isPresent()) {
            modelAndView.addObject("categoryGroup", categoryGroup.get());
            modelAndView.addObject("success", false);
            return modelAndView;

        } else {
            return new ModelAndView("errorPage");
        }
    }


    @PostMapping(value = "/create")
    public ModelAndView create(@Validated @ModelAttribute("categoryGroup") CategoryGroup categoryGroup, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        List<Category> categories = categoryService.findAll();
        modelAndView.addObject("categories", categories);

        if (bindingResult.hasFieldErrors()) {
            modelAndView.addObject("script", true);
        }
        else {
            String slug = AppUtils.removeNonAlphanumeric(categoryGroup.getName());

            Boolean existSlug = categoryGroupService.existsBySlugEqualsAndCategoryEquals(slug, categoryGroup.getCategory());

            if (existSlug) {
                modelAndView.addObject("error", "The name already exists");
            }
            else {
                try {
                    categoryGroup.setSlug(slug);
                    categoryGroupService.save(categoryGroup);

                    modelAndView.addObject("categoryGroup", new CategoryGroup());
                    modelAndView.addObject("success", "Successfully added new category group");
                } catch (Exception e) {
                    e.printStackTrace();
                    modelAndView.addObject("error", "Invalid data, please contact system administrator");
                }
            }
        }

        modelAndView.setViewName("cp/category-group/create");

        return modelAndView;
    }

    @PostMapping(value = "/edit/{id}")
    public ModelAndView update(@PathVariable long id, @Validated @ModelAttribute("categoryGroup") CategoryGroup categoryGroup, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        List<Category> categories = categoryService.findAll();
        modelAndView.addObject("categories", categories);

        if (bindingResult.hasFieldErrors()) {
            modelAndView.addObject("script", true);
        }
        else {

            String slug = AppUtils.removeNonAlphanumeric(categoryGroup.getName());

            Boolean existSlug = categoryGroupService.existsBySlugEqualsAndCategoryEqualsAndIdIsNot(slug, categoryGroup.getCategory(), id);

            if (existSlug) {
                modelAndView.addObject("error", "The name already exists");
            }
            else {
                try {
                    categoryGroup.setSlug(slug);
                    categoryGroupService.save(categoryGroup);

                    modelAndView.addObject("success", "Category Group has been successfully updated");
                } catch (Exception e) {
                    e.printStackTrace();
                    modelAndView.addObject("error", "Invalid data, please contact system administrator");
                }
            }
        }

        modelAndView.setViewName("cp/category-group/edit");

        return modelAndView;
    }
}
