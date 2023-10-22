package com.restapi.challenge5.Service.Impl;

import com.restapi.challenge5.Model.OrdersDetail;
import com.restapi.challenge5.Repository.OrderDetailRepository;
import com.restapi.challenge5.Service.OrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrdersDetail> getAllOrderDetail() {
        try {
            log.info("Retrieving All Order Detail.");
            return orderDetailRepository.findAll();
        } catch (Exception e) {
            log.error("Error while retrieving all Order Details: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve Order Details.", e);
        }
    }

    @Override
    public Boolean addNewOrderDetail(OrdersDetail orderDetail) {
        try {
            log.info("Adding a new Order Detail.");
            OrdersDetail newOrder = orderDetailRepository.save(orderDetail);
            if (newOrder != null) {
                return true;
            } else {
                log.error("Failed adding a new Order Detail");
                return false;
            }
        } catch (Exception e) {
            log.error("Error while adding a new OrderDetail: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to add a new OrderDetail.", e);
        }
    }
}
