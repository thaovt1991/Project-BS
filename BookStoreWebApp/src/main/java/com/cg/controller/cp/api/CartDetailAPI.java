package com.cg.controller.cp.api;

import com.cg.exception.DataInputException;
import com.cg.model.Cart;
import com.cg.model.CartDetail;
import com.cg.model.Customer;
import com.cg.model.Product;
import com.cg.model.dto.CartDetailDTO;
import com.cg.model.dto.OrderDTO;
import com.cg.service.cart.CartService;
import com.cg.service.cart_details.CartDetailService;
import com.cg.service.customer.CustomerService;
import com.cg.service.order.OrderService;
import com.cg.service.order_detail.OrderDetailService;
import com.cg.service.product.ProductService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/cp/api/cart-detail")
public class CartDetailAPI {

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CartDetailService cartDetailsService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AppUtils appUtils;


    @PostMapping("/create")
    public ResponseEntity<?> creatCartDetail(@Validated @RequestBody CartDetailDTO cartDetailDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Optional<Customer> customerOptional = customerService.findById(cartDetailDTO.getCustomerId());

        Optional<Product> productOptional = productService.findById(cartDetailDTO.getProductId());

        if (!productOptional.isPresent()) {
            throw new DataInputException("Product not exits !");
        }

        if (!customerOptional.isPresent()) {
            throw new DataInputException("Customer not exits !");
        }

        try {
            Cart cart = cartService.findCartsByCustomer(customerOptional.get());
            Product product = productOptional.get();
            CartDetail cartDetail = cartDetailDTO.toCartDetail(cart, product);
            cartDetailsService.save(cartDetail);
            return new ResponseEntity<>(cartDetail, HttpStatus.CREATED);
        } catch (DataInputException e) {
            throw new DataInputException("Cart detail creation information is not valid, please check the information again");
        }
    }
}
