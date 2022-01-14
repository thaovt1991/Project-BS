package com.cg.controller.cp.api;

import com.cg.exception.DataInputException;
import com.cg.model.*;
import com.cg.model.dto.OrderDTO;
import com.cg.service.cart.CartService;
import com.cg.service.cart_details.CartDetailService;
import com.cg.service.customer.CustomerService;
import com.cg.service.order.OrderService;
import com.cg.service.order_detail.OrderDetailService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cp/api/order")
public class OrderAIP {

    @Autowired
    private CartService cartService ;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService ;

    @Autowired
    private OrderDetailService orderDetailService ;

    @Autowired
    private CartDetailService cartDetailsService ;

    @Autowired
    private AppUtils appUtils;

    @PostMapping("/create")
    public ResponseEntity<?> creatOrder(@RequestBody OrderDTO orderDTO){
        Optional<Cart>  cartOptional = cartService.findById(orderDTO.getCartId());
        Optional<Customer> customerOptional = customerService.findById(orderDTO.getCustomerId()) ;
        if(!cartOptional.isPresent()){
            throw new DataInputException("Cart not exits !");
        }
        if(!customerOptional.isPresent()){
            throw new DataInputException("Customer not exits !");
        }



        Cart cart = cartOptional.get();

        Customer customer = customerOptional.get();

        if(cart.getCustomer().getId() != customer.getId()){
            throw new DataInputException("Data not true !");
        }

        try {
            List<CartDetail> cartDetailList = cartDetailsService.findCartDetailByCartAndDeletedIsFalse(cart) ;
            Order order = new Order();
            order.setCustomer(customer);
            orderService.save(order) ;

            BigDecimal totalAmount = BigDecimal.valueOf(0);
            for (CartDetail cartDetail : cartDetailList){
                OrderDetail orderDetail = cartDetail.toOrderDetail() ;
                orderDetail.setOrder(order);
                orderDetailService.save(orderDetail) ;
                totalAmount.add(orderDetail.getTotal()) ;
                cartDetail.setDeleted(true);
                cartDetailsService.save(cartDetail) ;
            }
            order.setTotalAmount(totalAmount);
            orderService.save(order) ;


            return new ResponseEntity<>(order, HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            throw new DataInputException("Order creation information is not valid, please check the information again");
        }
    }




}
