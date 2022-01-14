package com.cg.repository;

import com.cg.model.Cart;
import com.cg.model.CartDetail;
import com.cg.model.Product;
import com.cg.model.dto.CartDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

    List<CartDetail> findCartDetailByCartAndDeletedIsFalse(Cart cart);

    @Query("SELECT NEW com.cg.model.dto.CartDetailDTO (" +
            "cd.id, " +
            "cd.cart.id ," +
            "cd.product.id," +
            "cd.product.avatar," +
            "cd.product.name," +
            "cd.product.lastPrice," +
            "cd.quantity" +
            ") " +
            "FROM CartDetail cd " +
            "WHERE cd.cart = ?1 AND cd.deleted = false "
    )
    List<CartDetailDTO> findAllCartDetailDTOByCartAndDeletedFalse(Cart cart) ;

    CartDetail findCartDetailByProductAndAndDeletedIsFalse(Product product);
}
