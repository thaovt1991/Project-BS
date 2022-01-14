package com.cg.service.order_detail;

import com.cg.model.OrderDetail;
import com.cg.service.IGeneralService;

import java.util.List;

public interface OrderDetailService extends IGeneralService<OrderDetail> {

    List<OrderDetail> findAllByDeletedIsFalse();
}
