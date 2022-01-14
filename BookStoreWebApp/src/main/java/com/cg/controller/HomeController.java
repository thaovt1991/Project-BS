package com.cg.controller;


import com.cg.model.*;
import com.cg.service.cart.CartService;
import com.cg.service.cart_details.CartDetailService;
import com.cg.service.category.CategoryService;
import com.cg.service.categorygroup.CategoryGroupService;
import com.cg.service.customer.CustomerService;
import com.cg.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

@Controller

public class HomeController {

    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    @ModelAttribute("user")
    public User setUpUser() {
        return new User();
    }

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartDetailService cartDetailsService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryGroupService categoryGroupService;

    @Autowired
    private ProductService productService;


    @GetMapping
    public ModelAndView showHomePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        String title = "SunRise - Book Store ";
        System.out.println(getPrincipal());

        List<CategoryGroup> categoryGroups = categoryGroupService.findAll();

        List<Category> categories = categoryService.findAll();

        List<Product> productList = productService.findAll();


        if (getPrincipal().equals("anonymousUser") || customerService.findCustomerByUserUsername(getPrincipal())==null) {
            modelAndView.addObject("username", false);
        } else {
            Customer customer = customerService.findCustomerByUserUsername(getPrincipal());
            modelAndView.addObject("username", getPrincipal());
            modelAndView.addObject("customer", customer);

        }

        modelAndView.addObject("categoryGroups", categoryGroups);
        modelAndView.addObject("categories", categories);

        modelAndView.addObject("BestSellers", productList);
//        12 quyen sach duoc confirm order nhiu nhat
        modelAndView.addObject("SpecialOffer", productList);
//        6 quyen sach co rate sao cao nhat
        modelAndView.addObject("FeaturedProducts", productList);
//        12 quyen sach moi tao gan day nhat voi so luong = 0
        modelAndView.addObject("NewArrivals", productList);
//        12 quyen sach moi duoc nhap hang gan day nhat
        modelAndView.addObject("MostChoice", productList);
//        12 quyen sach duoc order nhieu nhat
        modelAndView.addObject("Category1", productList);
//        12 quyen sach trong loai sach duoc chon
        modelAndView.addObject("Category2", productList);
//        12 quyen sach trong loai sach duoc chon
        modelAndView.addObject("Category3", productList);
//        12 quyen sach trong loai sach duoc chon

        modelAndView.addObject("title", title);


        return modelAndView;
    }

    @GetMapping("/login-register.html")
    public ModelAndView showLoginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/login-register");


        if (getPrincipal().equals("anonymousUser") || customerService.findCustomerByUserUsername(getPrincipal())==null) {
            modelAndView.addObject("username", false);
        } else {
            Customer customer = customerService.findCustomerByUserUsername(getPrincipal());
            modelAndView.addObject("username", getPrincipal());
            modelAndView.addObject("customer", customer);

        }


        String title = "SunRise - Book Store";
        modelAndView.addObject("title", title);
        return modelAndView;
    }

    @GetMapping("/product-details.html")
    public ModelAndView showProductDetailPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/product-detail");


        if (getPrincipal().equals("anonymousUser") || customerService.findCustomerByUserUsername(getPrincipal())==null) {
            modelAndView.addObject("username", false);
        } else {
            Customer customer = customerService.findCustomerByUserUsername(getPrincipal());
            modelAndView.addObject("username", getPrincipal());
            modelAndView.addObject("customer", customer);

        }

        String title = "SunRise - Book Store";
        modelAndView.addObject("title", title);
        return modelAndView;
    }

    @GetMapping("/my-account.html")
//    public ModelAndView listCustomers(@CookieValue String JWT) {
    public ModelAndView listCustomers() {
        System.out.println(getPrincipal());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/my-account");

        String title = "SunRise - Book Store ";


        if (getPrincipal().equals("anonymousUser") || customerService.findCustomerByUserUsername(getPrincipal())==null) {
            modelAndView.addObject("username", false);
            modelAndView.setViewName("/login-register.html");
        } else {
            Customer customer = customerService.findCustomerByUserUsername(getPrincipal());
            modelAndView.addObject("username", getPrincipal());
            modelAndView.addObject("customer", customer);

        }

        modelAndView.addObject("title", title);
        return modelAndView;
    }

    @GetMapping("/cart.html")
    public ModelAndView showCartPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/cart");
        String title = "SunRise - Book Store";


        if (getPrincipal().equals("anonymousUser") || customerService.findCustomerByUserUsername(getPrincipal())==null) {
            modelAndView.addObject("username", false);
            modelAndView.setViewName("/login-register.html");
        } else {
            Customer customer = customerService.findCustomerByUserUsername(getPrincipal());

            modelAndView.addObject("username", getPrincipal());
            modelAndView.addObject("customer", customer);
            Cart cart = cartService.findCartsByCustomer(customer) ;
            modelAndView.addObject("cart", cart);

        }
        modelAndView.addObject("title", title);
        return modelAndView;
    }

    @GetMapping("/order-complete.html")
    public ModelAndView showOrderCompletePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/order-complete");
        String title = "SunRise - Book Store";


        if (getPrincipal().equals("anonymousUser") || customerService.findCustomerByUserUsername(getPrincipal())==null) {
            modelAndView.addObject("username", false);
            modelAndView.setViewName("/login-register.html");
        } else {
            Customer customer = customerService.findCustomerByUserUsername(getPrincipal());

            modelAndView.addObject("username", getPrincipal());
            modelAndView.addObject("customer", customer);

        }


        modelAndView.addObject("title", title);
        return modelAndView;
    }

    @GetMapping("/checkout.html")
    public ModelAndView showCheckoutPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("checkout");
        String title = "SunRise - Book Store ";


        if (getPrincipal().equals("anonymousUser") || customerService.findCustomerByUserUsername(getPrincipal())==null) {
            modelAndView.addObject("username", false);
            modelAndView.setViewName("/login-register.html");
        } else {
            Customer customer = customerService.findCustomerByUserUsername(getPrincipal());

            Cart cart = cartService.findCartsByCustomer(customer) ;

            List<CartDetail> cartDetailList = cartDetailsService.findCartDetailByCartAndDeletedIsFalse(cart) ;



            BigDecimal sub_total = BigDecimal.valueOf(0);
            BigDecimal grand_total = BigDecimal.valueOf(0);
            BigDecimal money_details ;
            for (CartDetail cartDetail : cartDetailList){
                money_details = cartDetail.getProduct().getLastPrice().multiply(BigDecimal.valueOf(cartDetail.getQuantity()));
                sub_total = sub_total.add(money_details) ;
            }

            grand_total = sub_total ;// co the nhan voi phi ship

            modelAndView.addObject("username", getPrincipal());
            modelAndView.addObject("customer", customer);
            modelAndView.addObject("cart", cart);
            modelAndView.addObject("cartDetailList", cartDetailList);
            modelAndView.addObject("sub_total", sub_total);
            modelAndView.addObject("grand_total", grand_total);

        }

        modelAndView.addObject("title", title);
        return modelAndView;
    }

    @GetMapping("/logout-customer.html")
    private ModelAndView eraseCookie(HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null)
            for (Cookie cookie : cookies) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
            }

        ModelAndView modelAndView = new ModelAndView("/login-register.html");
        return modelAndView;
    }

}

