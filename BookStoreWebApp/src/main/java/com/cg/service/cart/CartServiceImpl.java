package com.cg.service.cart;

import com.cg.model.Cart;
import com.cg.model.Customer;
import com.cg.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return cartRepository.findById(id);
    }

    @Override
    public Cart getById(Long id) {
        return cartRepository.getById(id);
    }

    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public void remove(Long id) {
        cartRepository.deleteById(id);
    }

    @Override
    public Cart findCartsByCustomer(Customer customer) {
        return cartRepository.findCartsByCustomer(customer);
    }
}
