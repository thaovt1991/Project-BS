package com.cg.controller.cp;

import com.cg.model.Category;
import com.cg.model.CategoryGroup;
import com.cg.model.Customer;
import com.cg.model.dto.CategoryDTO;
import com.cg.service.category.CategoryService;
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
@RequestMapping("/cp/categories")
public class CategoryCPController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ModelAndView showList() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("cp/category/list");

        Iterable<Category> categories = categoryService.findAllByDeletedIsFalse();

        modelAndView.addObject("categories", categories);

        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreate() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("cp/category/create");
        modelAndView.addObject("categoryDTO", new CategoryDTO());
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEdit(@PathVariable Long id) {

        ModelAndView modelAndView = new ModelAndView();

        Optional<Category> category = categoryService.findById(id);

        if (category.isPresent()) {
            modelAndView.addObject("category", category);

        } else {
            modelAndView.addObject("category", new Category());
            modelAndView.addObject("script", false);
            modelAndView.addObject("success", false);
            modelAndView.addObject("error", "Invalid category information");
        }
        modelAndView.setViewName("cp/category/edit");

        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView showDelete(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Category> category = categoryService.findById(id);

        if (category.isPresent()) {
            modelAndView.addObject("category", category.get());
            modelAndView.addObject("success", false);

        } else {
            Category category1 = new Category();
            category1.setDeleted(true);
            modelAndView.addObject("category",category1 );
            modelAndView.addObject("error", "Invalid category information");


        }
        modelAndView.setViewName("cp/category/delete");
        return modelAndView;

    }

    @PostMapping(value = "/create")
    public ModelAndView create(@Validated @ModelAttribute("categoryDTO") CategoryDTO categoryDTO, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasFieldErrors()) {
            modelAndView.addObject("script", true);
        }
        else {
            String slug = AppUtils.removeNonAlphanumeric(categoryDTO.getName());

            Boolean existSlug = categoryService.existsBySlugEquals(slug);

            if (existSlug) {
                modelAndView.addObject("error", "The name already exists");
            }
            else {
                try {
                    categoryDTO.setSlug(slug);

                    Category category = categoryService.create(categoryDTO);

                    modelAndView.addObject("categoryDTO", new CategoryDTO());
                    modelAndView.addObject("success", "Successfully added new category");
                } catch (Exception e) {
                    e.printStackTrace();
                    modelAndView.addObject("error", "Invalid data, please contact system administrator");
                }
            }
        }

        modelAndView.setViewName("cp/category/create");

        return modelAndView;
    }

    @PostMapping(value = "/edit/{id}")
    public ModelAndView update(@PathVariable long id, @Validated @ModelAttribute("category") Category category, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasFieldErrors()) {
            modelAndView.addObject("script", true);
        }
        else {

            String slug = AppUtils.removeNonAlphanumeric(category.getName());

            Boolean existSlug = categoryService.existsBySlugEqualsAndIdIsNot(slug, id);

            if (existSlug) {
                modelAndView.addObject("error", "The name already exists");
            }
            else {
                try {
                    category.setSlug(slug);
                    categoryService.save(category);

                    modelAndView.addObject("success", "Category has been successfully updated");
                } catch (Exception e) {
                    e.printStackTrace();
                    modelAndView.addObject("error", "Invalid data, please contact system administrator");
                }
            }

        }

        modelAndView.setViewName("cp/category/edit");

        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deleteCategory(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("/cp/category/delete");

        Optional<Category> category = categoryService.findById(id);

        if (category.isPresent()) {
            category.get().setDeleted(true);
            categoryService.save(category.get());
            modelAndView.addObject("category", category.get());
            modelAndView.addObject("success", "Category deleted successfully");
            return modelAndView;
        } else {
            return new ModelAndView("/errorPage");
        }
    }
}
