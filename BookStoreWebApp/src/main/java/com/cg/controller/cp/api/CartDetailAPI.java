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
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

            CartDetail cartDetail = cartDetailsService.findCartDetailByProductAndAndDeletedIsFalse(product);

            if(cartDetail !=null){
             int quantityNew = cartDetailDTO.getQuantity() + cartDetail.getQuantity() ;
             cartDetail.setQuantity(quantityNew);
            }else{
                 cartDetail = cartDetailDTO.toCartDetail(cart,product) ;
            }
            cartDetailsService.save(cartDetail);
            return new ResponseEntity<>(cartDetail, HttpStatus.CREATED);
        } catch (DataInputException e) {
            throw new DataInputException("Cart detail creation information is not valid, please check the information again");
        }
    }

    @GetMapping("/cart/{id}")
    public ResponseEntity<List<?>> showAllCart(@PathVariable Long id){
        Optional<Cart> cartOptional = cartService.findById(id) ;
        if(!cartOptional.isPresent()){
            throw new DataInputException("Cart not exits !");
        }
        try {
            List<CartDetailDTO> cartDetailDTOList = cartDetailsService.findAllCartDetailDTOByCartAndDeletedFalse(cartOptional.get());
            return new ResponseEntity<>(cartDetailDTOList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/remove/{id}")
    public ResponseEntity<List<?>> showAllCartAfterRemove(@PathVariable Long id){
        Optional<CartDetail> cartDetailOptional = cartDetailsService.findById(id) ;
        if(!cartDetailOptional.isPresent()){
            throw new DataInputException("Cart not exits !");
        }
        try {
            CartDetail cartDetail = cartDetailOptional.get() ;
            cartDetail.setDeleted(true);
            cartDetailsService.save(cartDetail) ;
            Cart cart = cartDetail.getCart() ;
            List<CartDetailDTO> cartDetailDTOList = cartDetailsService.findAllCartDetailDTOByCartAndDeletedFalse(cart);
            return new ResponseEntity<>(cartDetailDTOList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
