package com.cg.model.dto;

import com.cg.model.Cart;
import com.cg.model.CartDetail;
import com.cg.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CartDetailDTO {
    private Long id;

    private Long customerId;

    private Long productId;

    @Min(value = 1, message = "Quantity must be more than 1 !")
    private int quantity;

    public CartDetail toCartDetail(Cart cart , Product product){
        CartDetail cartDetail = new CartDetail() ;
        cartDetail.setCart(cart);
        cartDetail.setProduct(product);
        cartDetail.setQuantity(quantity) ;
        return cartDetail ;
    }
}
