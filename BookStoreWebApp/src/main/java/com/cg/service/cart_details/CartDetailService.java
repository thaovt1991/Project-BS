package com.cg.service.cart_details;

import com.cg.model.Cart;
import com.cg.model.CartDetail;
import com.cg.service.IGeneralService;

import java.util.List;

public interface CartDetailService extends IGeneralService<CartDetail> {

    List<CartDetail> findCartDetailByCartAndDeletedIsFalse(Cart cart);
}
