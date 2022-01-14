package com.cg.controller.cp.api;


import com.cg.exception.DataInputException;
import com.cg.exception.ResourceNotFoundException;
import com.cg.model.Product;
import com.cg.model.dto.IProductDTO;
import com.cg.model.dto.ProductDTO;
import com.cg.service.product.ProductService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/cp/api/products")
public class ProductAPI {

    @Autowired
    private ProductService productService;

    @Autowired
    private AppUtils appUtils;


    @GetMapping("/getById/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable Long id)  {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Can not found product with the Id: " + id);
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<?>> findAll() {
        try {
            Iterable<IProductDTO> iProductDTOS = productService.findAllIProductDTO();

            if (((List) iProductDTOS).isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(iProductDTOS, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Validated ProductDTO productDTO, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        try {
            Product product = productService.create(productDTO);

            IProductDTO iProductDTO =  productService.findIProductDTOById(product.getId());

            return new ResponseEntity<>(iProductDTO, HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            throw new DataInputException("Product creation information is not valid, please check the information again");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws IOException {

        Optional<Product> product = productService.findById(id);

        if (product.isPresent()) {
            productService.delete(product.get());

            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            throw new DataInputException("Invalid product information");
        }
    }

}