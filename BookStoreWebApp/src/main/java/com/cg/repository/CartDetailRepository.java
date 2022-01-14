package com.cg.repository;

import com.cg.model.Cart;
import com.cg.model.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

    List<CartDetail> findCartDetailByCartAndDeletedIsFalse(Cart cart);
}
