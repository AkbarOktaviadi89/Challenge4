package com.binarfud.challenge4.service;

import com.binarfud.challenge4.model.*;
import com.binarfud.challenge4.model.response.OrdersResponse;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Order;
import java.util.List;

@Service
public interface OrderService {
    List<OrdersResponse> findAllOrders();

    Boolean createNewOrders(Long userId, Long productCode, String destinationAddress, int productQty, boolean isOpen);

    Page<OrdersResponse> getOrdersPaged(int page);

}
