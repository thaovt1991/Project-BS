package com.cg.model.dto;

import com.cg.model.Cart;
import com.cg.model.CartDetail;
import com.cg.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CartDetailDTO {
    private Long id;

    private Long customerId;

    private Long cartId ;

    private Long productId;

    private String productImage ;

    private String productName ;

    private BigDecimal productLastPrice ;


    @Min(value = 1, message = "Quantity must be more than 1 !")
    @Max(value = 1000, message = "Quantity must be less than 1.000 !")
    private int quantity;

    public CartDetailDTO(Long id, Long cartId, Long productId, String productImage, String productName, BigDecimal productLastPrice, int quantity) {
        this.id = id;
        this.cartId = cartId;
        this.productId = productId;
        this.productImage = productImage;
        this.productName = productName;
        this.productLastPrice = productLastPrice;
        this.quantity = quantity;
    }

    public CartDetail toCartDetail(Cart cart , Product product){
        CartDetail cartDetail = new CartDetail() ;
        cartDetail.setId(id);
        cartDetail.setCart(cart);
        cartDetail.setProduct(product);
        cartDetail.setQuantity(quantity) ;
        return cartDetail ;
    }
}
