package com.cg.service.cart_details;

import com.cg.model.Cart;
import com.cg.model.CartDetail;
import com.cg.model.Product;
import com.cg.model.dto.CartDetailDTO;
import com.cg.repository.CartDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class CartDetailServiceImpl implements CartDetailService {

    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Override
    public List<CartDetail> findAll() {
        return cartDetailRepository.findAll();
    }

    @Override
    public Optional<CartDetail> findById(Long id) {
        return cartDetailRepository.findById(id);
    }

    @Override
    public CartDetail getById(Long id) {
        return cartDetailRepository.getById(id);
    }

    @Override
    public CartDetail save(CartDetail cartDetail) {
        return cartDetailRepository.save(cartDetail);
    }

    @Override
    public void remove(Long id) {
        cartDetailRepository.deleteById(id);
    }

    @Override
    public List<CartDetail> findCartDetailByCartAndDeletedIsFalse(Cart cart) {
        return cartDetailRepository.findCartDetailByCartAndDeletedIsFalse(cart);
    }

    @Override
    public List<CartDetailDTO> findAllCartDetailDTOByCartAndDeletedFalse(Cart cart) {
        return cartDetailRepository.findAllCartDetailDTOByCartAndDeletedFalse(cart);
    }

    public CartDetail findCartDetailByProductAndAndDeletedIsFalse(Product product){
        return cartDetailRepository.findCartDetailByProductAndAndDeletedIsFalse(product) ;
    };
}
