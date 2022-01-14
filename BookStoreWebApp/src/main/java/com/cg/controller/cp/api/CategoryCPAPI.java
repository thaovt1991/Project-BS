package com.cg.controller.cp.api;


import com.cg.model.Category;
import com.cg.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cp/api/categories")
public class CategoryCPAPI {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Iterable<?>> findAll() {
        try {
            List<Category> categories = categoryService.findAll();

            if (categories.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
