package com.cg.controller.cp.api;

import com.cg.exception.DataInputException;
import com.cg.exception.EmailExistsException;
import com.cg.model.*;
//import com.cg.model.dto.UserDTO;
import com.cg.model.dto.UserDTO;
import com.cg.service.cart.CartService;
import com.cg.service.customer.CustomerService;
import com.cg.service.jwt.JwtService;
import com.cg.service.role.RoleService;
import com.cg.service.staff.StaffService;
import com.cg.service.user.UserService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("cp/api/auth")

public class AuthAPI {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CartService cartService ;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AppUtils appUtils;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return appUtils.mapErrorToResponse(bindingResult);

        Optional<UserDTO> optUser = userService.findUserDTOByUsername(userDTO.getUsername());

        if (optUser.isPresent()) {
            throw new EmailExistsException("Email already exists");
        }

        Optional<Role> optRole = roleService.findById(userDTO.getRole().getId());

        if (!optRole.isPresent()) {
            throw new DataInputException("Invalid account role");
        }

        Staff staff = staffService.findById(userDTO.getStaffId()).get() ;


        try {
            User user = userService.save(userDTO.toUser());
            if (staff != null) {
                staff.setUserId(user.getId());
                staffService.save(staff);
            }
            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            throw new DataInputException("Account information is not valid, please check the information again");
        }
    }

    @PostMapping("/register-customer")
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return appUtils.mapErrorToResponse(bindingResult);

        Optional<UserDTO> optUser = userService.findUserDTOByUsername(userDTO.getUsername());

        if (optUser.isPresent()) {
            throw new EmailExistsException("Email already exists");
        }

        Optional<Role> optRole = roleService.findById(userDTO.getRole().getId());

        if (!optRole.isPresent()) {
            throw new DataInputException("Invalid account role");
        }

        try {

            User user = userService.save(userDTO.toUser());
            Customer customer = userDTO.toCustomer(user) ;
            customerService.save(customer);
            Cart cart = new Cart() ;
            cart.setCustomer(customer);
            cartService.save(cart);
            return new ResponseEntity<>(user,HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            throw new DataInputException("Account information is not valid, please check the information again");
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateTokenLogin(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.getByUsername(user.getUsername());

        JwtResponse jwtResponse = new JwtResponse(
                jwt,
                currentUser.getId(),
                userDetails.getUsername(),
                currentUser.getUsername(),
                userDetails.getAuthorities()
        );

        ResponseCookie springCookie = ResponseCookie.from("JWT", jwt)
                .httpOnly(false)
                .secure(false)
                .path("/")
                .maxAge(60 * 1000)
                .domain("localhost")
                .build();

        System.out.println(jwtResponse);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, springCookie.toString())
                .body(jwtResponse);

    }

//    @PostMapping("/login-customer")
//    public ResponseEntity<?> loginCustomer(@RequestBody User user) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String jwt = jwtService.generateTokenLogin(authentication);
//
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        User currentUser = userService.getByUsername(user.getUsername());
//
//        JwtResponse jwtResponse = new JwtResponse(
//                jwt,
//                currentUser.getId(),
//                userDetails.getUsername(),
//                currentUser.getUsername(),
//                userDetails.getAuthorities()
//        );
//
//        ResponseCookie springCookie = ResponseCookie.from("JWT", jwt)
//                .httpOnly(false)
//                .secure(false)
//                .path("/")
//                .maxAge(60 * 1000)
//                .domain("localhost")
//                .build();
//
//        System.out.println(jwtResponse);
//
//        return ResponseEntity
//                .ok()
//                .header(HttpHeaders.SET_COOKIE, springCookie.toString())
//                .body(jwtResponse);
//
//    }

}
