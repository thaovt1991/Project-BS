package com.cg.service.order;

import com.cg.model.Order;
import com.cg.service.IGeneralService;

import java.util.List;

public interface OrderService extends IGeneralService<Order> {

    List<Order> findAllByDeletedIsFalse() ;
}
