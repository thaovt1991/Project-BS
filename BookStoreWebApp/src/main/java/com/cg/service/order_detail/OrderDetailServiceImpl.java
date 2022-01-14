package com.cg.service.order_detail;

import com.cg.model.OrderDetail;
import com.cg.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository ;

    @Override
    public List<OrderDetail> findAll() {
        return orderDetailRepository.findAll();
    }

    @Override
    public Optional<OrderDetail> findById(Long id) {
        return orderDetailRepository.findById(id);
    }

    @Override
    public OrderDetail getById(Long id) {
        return orderDetailRepository.getById(id);
    }

    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public void remove(Long id) {
       orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetail> findAllByDeletedIsFalse() {
        return orderDetailRepository.findAllByDeletedIsFalse();
    }
}
