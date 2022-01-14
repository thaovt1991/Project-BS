package com.cg.model;

import com.cg.model.dto.CartDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cart_details")
public class CartDetail extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;


    public OrderDetail toOrderDetail(){
        OrderDetail orderDetail = new OrderDetail() ;
        orderDetail.setPrice(product.getLastPrice());
        orderDetail.setQuantity(quantity);
        orderDetail.setNameProduct(product.getName());
        BigDecimal total = product.getLastPrice().multiply(BigDecimal.valueOf(quantity)) ;
        orderDetail.setTotal(total);
        orderDetail.setProductId(product.getId());
        return orderDetail ;
    }


    public CartDetailDTO cartDetailDTO(){
        CartDetailDTO cartDetailDTO = new CartDetailDTO() ;
        cartDetailDTO.setId(id) ;
        cartDetailDTO.setCartId(cart.getId()) ;
        cartDetailDTO.setQuantity(quantity) ;
        cartDetailDTO.setProductImage(product.getAvatar()) ;
        cartDetailDTO.setProductLastPrice(product.getLastPrice());
        cartDetailDTO.setProductName(product.getName()) ;
        return cartDetailDTO ;
    }

}
