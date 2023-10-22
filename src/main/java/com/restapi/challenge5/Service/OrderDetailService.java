package com.restapi.challenge5.Service;

import com.restapi.challenge5.Model.OrdersDetail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderDetailService {

    List<OrdersDetail> getAllOrderDetail();

    Boolean addNewOrderDetail(OrdersDetail orderDetail);
}
