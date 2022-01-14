package com.cg.controller.cp;


import com.cg.model.Category;
import com.cg.model.Product;
import com.cg.service.category.CategoryService;
import com.cg.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cp/products")
public class ProductCPController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public ModelAndView showCpProductIndex() {
        List<Product> products = productService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("products", products);
        modelAndView.setViewName("cp/product/list");
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCpProductCreate() {

        ModelAndView modelAndView = new ModelAndView();

        List<Category> categories = categoryService.findAll();

        modelAndView.addObject("categories", categories);
        modelAndView.addObject("product", new Product());

        modelAndView.setViewName("cp/product/create");

        return modelAndView;
    }

    @GetMapping("/upload")
    public ModelAndView showCpProductUpload() {

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("cp/product/upload");

        return modelAndView;
    }

//    @PostMapping(value = "/create")
//    public ModelAndView create(@Validated @ModelAttribute("product") Product product, BindingResult bindingResult) {
//        ModelAndView modelAndView = new ModelAndView();
//
//        List<Category> categories = CategoryService.findAll();
//
//        modelAndView.addObject("categories", categories);
//
//        if (bindingResult.hasFieldErrors()) {
//            modelAndView.addObject("script", true);
//        }
//        else {
//            String slug = AppUtils.removeNonAlphanumeric(product.getName());
//
//            Boolean existSlug = productService.existsBySlugEquals(slug);
//
//            if (existSlug) {
//                modelAndView.addObject("error", "The name already exists");
//            }
//            else {
//                try {
//                    product.setSlug(slug);
//                    productService.save(product);
//
//                    modelAndView.addObject("product", new Product());
//                    modelAndView.addObject("success", "Successfully added new product");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    modelAndView.addObject("error", "Invalid data, please contact system administrator");
//                }
//            }
//        }
//
//        modelAndView.setViewName("cp/product/create");
//
//        return modelAndView;
//    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEdit(@PathVariable Long id) {

        ModelAndView modelAndView = new ModelAndView();

        Optional<Product> product = productService.findById(id);

        if (product.isPresent()) {
            modelAndView.addObject("product", product);
        } else {
            modelAndView.addObject("product", new Product());
            modelAndView.addObject("script", false);
            modelAndView.addObject("success", false);
            modelAndView.addObject("error", "Invalid product information");
        }

        modelAndView.setViewName("cp/product/edit");

        return modelAndView;
    }

}
