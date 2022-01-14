package com.cg.repository;

import com.cg.model.Cart;
import com.cg.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findCartsByCustomer(Customer customer) ;
}
